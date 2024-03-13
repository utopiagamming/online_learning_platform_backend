package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjRoleMenuMapper;
import cn.exam.service.ZjRoleMenuService;
import cn.exam.vo.MenuInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
@Service
@Transactional
public class ZjRoleMenuServiceImpl implements ZjRoleMenuService {

    @Autowired
    private ZjRoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuInfoVO> queryMenuList(List<String> roleIdList) {
        List<MenuInfoVO> menuList = roleMenuMapper.queryMenuList(roleIdList);
        //一级菜单
        List<MenuInfoVO> menuVOS0 = menuList.stream().filter(f -> f.getMenuDegree().equals(0)).collect(Collectors.toList());
        //二级菜单
        List<MenuInfoVO> menuVOS1 = menuList.stream().filter(f -> f.getMenuDegree().equals(1)).collect(Collectors.toList());
        // 二级菜单去重
        ArrayList<MenuInfoVO> infos2 = menuVOS1.stream()
                        .collect(Collectors.collectingAndThen(Collectors
                                .toCollection(() -> new TreeSet<>(Comparator
                                        .comparing(MenuInfoVO::getMenuId))), ArrayList::new));
        // 将二级菜单中 parent_id 等于 一级菜单的此 menu_id 的添加到其 subs 属性上
        menuVOS0.forEach(y->{
            List<MenuInfoVO> collect = infos2.stream().filter(q -> q.getParentId().equals(y.getMenuId())).collect(Collectors.toList());
            collect.forEach(q-> q.setIndex(q.getMenuIndex()));
            y.setIndex(y.getMenuIndex());
            y.setSubs(collect);
        });
        return menuVOS0;
    }
}
