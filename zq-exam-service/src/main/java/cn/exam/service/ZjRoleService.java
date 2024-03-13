package cn.exam.service;

import cn.exam.domain.zj.ZjRole;
import cn.exam.query.RoleQuery;
import cn.exam.util.PageResult;

import java.util.List;

public interface ZjRoleService {
    /**
     * 分页
     */
    PageResult<List<ZjRole>> queryPage(RoleQuery query);


    void insertRoleInfo(ZjRole role);

    void deleteRole(Integer id);
}
