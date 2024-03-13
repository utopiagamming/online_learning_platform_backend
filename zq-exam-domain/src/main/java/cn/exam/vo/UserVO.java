package cn.exam.vo;

import cn.exam.vo.UserRoleVO;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 密码MD5
     */
    private String password;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 是否删除 0 否  1是
     */
    private Integer isDelete;
    /**
     * 开始时间
     */
    private String createTime;
    /**
     * 结束时间
     */
    private String updateTime;
    private Integer classId;

    private String token;
    private List<UserRoleVO> role;
}
