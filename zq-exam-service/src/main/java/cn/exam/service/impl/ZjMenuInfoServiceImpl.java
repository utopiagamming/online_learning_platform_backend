package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjMenuInfoMapper;
import cn.exam.dao.mapper.zj.ZjRoleMenuMapper;
import cn.exam.domain.zj.ZjMenuInfo;
import cn.exam.domain.zj.ZjRoleMenu;
import cn.exam.query.ZjMenuQuery;
import cn.exam.service.ZjMenuInfoService;
import cn.exam.so.RoleMenuIdSO;
import cn.exam.util.*;
import cn.exam.vo.MenuInfoVO;
import cn.exam.vo.RoleMenuVO;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZjMenuInfoServiceImpl implements ZjMenuInfoService {

    @Autowired
    private ZjMenuInfoMapper menuInfoMapper;

    @Autowired
    private ZjRoleMenuMapper roleMenuMapper;

    @Override
    public PageResult<List<ZjMenuInfo>> queryMenuInfoPage(ZjMenuQuery query) {
        return PageUtil.execute(() -> menuInfoMapper.queryPage(query), query);
    }

    @Override
    public List<Integer> queryMenuIdListByRoleId(String roleId) {
        List<Integer> integers = menuInfoMapper.queryMenuIdListByRoleId(roleId);
        return integers.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<RoleMenuVO> queryMenuTreeByRoleId(String roleId) {
        List<RoleMenuVO> roleMenuList = new ArrayList<>();
        List<ZjMenuInfo> ksMenuInfos = menuInfoMapper.selectAll();
        List<ZjMenuInfo> collect = ksMenuInfos.stream().filter(f -> f.getMenuStatus().equals(1)).collect(Collectors.toList());
        List<ZjMenuInfo> Menu0 = collect.stream().filter(q -> q.getMenuDegree().equals(0)).collect(Collectors.toList());
        List<ZjMenuInfo> Menu1 = collect.stream().filter(q -> q.getMenuDegree().equals(1)).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        list.add(roleId);
        Map<Integer, String> menuMap = new HashMap<>();
        List<MenuInfoVO> menuInfoVOS = roleMenuMapper.queryMenuList(list);
        menuInfoVOS.forEach(f -> menuMap.put(f.getMenuId(), null));
        for (ZjMenuInfo info : Menu0) {
            RoleMenuVO menuVO = new RoleMenuVO();
            menuVO.setId(info.getMenuId());
            menuVO.setLabel(info.getMenuName());
            if (menuMap.containsKey(info.getMenuId())) {
                menuVO.setStatus(1);
            } else {
                menuVO.setStatus(0);
            }
            List<RoleMenuVO> roleMenu = new ArrayList<>();
            List<ZjMenuInfo> collect1 = Menu1.stream().filter(q -> q.getParentId().equals(info.getMenuId())).collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(collect1)) {
                for (ZjMenuInfo menuInfo : collect1) {
                    RoleMenuVO children = new RoleMenuVO();
                    children.setId(menuInfo.getMenuId());
                    children.setLabel(menuInfo.getMenuName());
                    if (menuMap.containsKey(menuInfo.getMenuId())) {
                        children.setStatus(1);
                    } else {
                        children.setStatus(0);
                    }
                    roleMenu.add(children);
                }
            }
            menuVO.setChildren(roleMenu);

            roleMenuList.add(menuVO);
        }
        return roleMenuList;
    }

    // 插入时生成的id非常混乱，可以修改一下
    @Override
    public void updateRoleMenuInfo(RoleMenuIdSO so) {
        if (ObjectUtils.isEmpty(so.getRoleId())) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "角色id为空");
        }
        List<ZjRoleMenu> ksRoleMenus = roleMenuMapper.queryRoleMenuInfoByRoleId(so.getRoleId());
        ksRoleMenus.forEach(f -> roleMenuMapper.deleteByPrimaryKey(f.getId()));
        String currentDateTime = DateUtil.getCurrentDateTime();
        List<ZjRoleMenu> list = new ArrayList<>();
        JSONArray objects = JSONArray.parseArray(so.getMenuIdString());
        for (Object s : objects) {
            if (!ObjectUtils.isEmpty(s)){
                ZjRoleMenu menu = new ZjRoleMenu();
                menu.setMenuId(Integer.valueOf(s.toString()));
                menu.setRoleId(so.getRoleId());
                // menu.setCreateTime(currentDateTime);
                // menu.setUpdateTime(currentDateTime);
                list.add(menu);
            }
        }
        if (!ObjectUtils.isEmpty(list)){
            roleMenuMapper.insertList(list);
        }

    }

    @Override
    public void insertMenuInfo(ZjMenuInfo menuInfo) {
        menuInfoMapper.insertSelective(menuInfo);
    }

}
