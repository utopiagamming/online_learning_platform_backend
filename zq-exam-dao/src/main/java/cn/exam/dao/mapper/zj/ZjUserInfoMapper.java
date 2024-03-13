package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjUserInfo;
import cn.exam.query.UserQuery;
import cn.exam.vo.UserPageVO;
import cn.exam.vo.UserRoleVO;
import cn.exam.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Repository
public interface ZjUserInfoMapper extends CommonBaseMapper<ZjUserInfo> {
//    @Select("select  user_id userId,password,user_name userName from zj_user_info where user_id =#{userId} and is_delete=0")
    UserVO queryShiroUserInfoByUserName(@Param("userId") String userId);

    /**
     * userId查询权限
     * @param userId 用户id
     *
     */
    @Select("SELECT   a.user_id userId,a.role_id roleId, b.role_name roleName FROM zj_user_info a LEFT JOIN zj_role b ON a.role_id = b.role_id WHERE a.user_id=#{userId}")
    List<UserRoleVO> queryUserRoleByUserId(@Param("userId")String userId);

    List<UserPageVO>queryPage(UserQuery query);

    List<ZjUserInfo> queryListByClassId(Integer classId);
}
