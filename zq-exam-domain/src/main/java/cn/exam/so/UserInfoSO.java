package cn.exam.so;

import lombok.Data;

@Data
public class UserInfoSO {
    /**
     * 学号 或 教师号
     */
    private String userId;

    private String roleId;

    /**
     * 密码
     */
    private String password;

    private String userName;

    private String confirmPassword;

    private String isDelete;

    private Integer classId;

}
