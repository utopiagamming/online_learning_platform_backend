package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.vo.MenuInfoVO;
import cn.exam.domain.zj.ZjRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjRoleMenuMapper extends CommonBaseMapper<ZjRoleMenu> {

    List<MenuInfoVO> queryMenuList(@Param("roleIdList") List<String> roleIdList);

    List<ZjRoleMenu> queryRoleMenuInfoByRoleId(String roleId);

}
