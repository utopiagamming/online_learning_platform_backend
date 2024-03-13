package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjRoleMapper;
import cn.exam.dao.mapper.zj.ZjRoleMenuMapper;
import cn.exam.domain.zj.ZjRole;
import cn.exam.domain.zj.ZjRoleMenu;
import cn.exam.query.RoleQuery;
import cn.exam.service.ZjRoleService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZjRoleServiceImpl implements ZjRoleService {
    @Autowired
    private ZjRoleMapper roleMapper;
    @Autowired
    private ZjRoleMenuMapper roleMenuMapper;
    @Override
    public PageResult<List<ZjRole>> queryPage(RoleQuery query) {
        return PageUtil.execute(()->roleMapper.queryPage(query),query);
    }

    @Override
    public void insertRoleInfo(ZjRole role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void deleteRole(Integer id) {
        ZjRole tblRole = roleMapper.selectByPrimaryKey(id);
        List<ZjRoleMenu> list = roleMenuMapper.queryRoleMenuInfoByRoleId(tblRole.getRoleId());
        list.forEach(f-> roleMenuMapper.deleteByPrimaryKey(f.getId()));
        roleMapper.deleteByPrimaryKey(id);
    }
}
