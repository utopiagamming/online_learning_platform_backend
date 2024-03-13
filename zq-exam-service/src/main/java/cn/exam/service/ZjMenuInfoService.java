package cn.exam.service;

import cn.exam.domain.zj.ZjMenuInfo;
import cn.exam.query.ZjMenuQuery;
import cn.exam.so.RoleMenuIdSO;
import cn.exam.util.PageResult;
import cn.exam.vo.RoleMenuVO;

import java.util.List;

public interface ZjMenuInfoService {

    PageResult<List<ZjMenuInfo>> queryMenuInfoPage(ZjMenuQuery query);


    /**
     * 当前roleId有哪些菜单id
     */
    List<Integer> queryMenuIdListByRoleId(String roleId);


    /**
     * 根据roleId查询菜单树
     * @param roleId 角色id
     */
    List<RoleMenuVO> queryMenuTreeByRoleId(String roleId);


    /**
     * 更新当前角色下面的菜单
     */
    void updateRoleMenuInfo(RoleMenuIdSO so);


    /**
     * 新增
     * @param menuInfo 菜单实体类
     */
    void insertMenuInfo(ZjMenuInfo menuInfo);

}
