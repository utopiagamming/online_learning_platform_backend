package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_role")
public class ZjRole implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     * 角色id
     */
    @Column(name ="role_id")
    private String roleId;
    /**
     *
     */
    @Column(name ="role_name")
    private String roleName;
//    /**
//     *
//     */
//    @Column(name ="create_time")
//    private String createTime;
//    /**
//     *
//     */
//    @Column(name ="update_time")
//    private String updateTime;
}
