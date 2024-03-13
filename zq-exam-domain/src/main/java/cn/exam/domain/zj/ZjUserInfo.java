package cn.exam.domain.zj;

import cn.exam.vo.UserRoleVO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "zj_user_info")
public class ZjUserInfo  {
    /**
     * 用户id
     */
    @Id
    @Column(name ="user_id")
    private String userId;

    /**
     * 密码MD5
     */
    @Column(name ="password")
    private String password;

    /**
     * 用户名
     */
    @Column(name ="user_name")
    private String userName;
    /**
     * 是否删除 0 否  1是
     */
    @Column(name ="is_delete")
    private Integer isDelete;

    @Column(name ="class_id")
    private Integer classId;
    /**
     * 开始时间
     */
    @Column(name ="create_time")
    private String createTime;
    /**
     * 结束时间
     */
    @Column(name ="update_time")
    private String updateTime;

    @Column(name ="role_id")
    private String roleId;

}
