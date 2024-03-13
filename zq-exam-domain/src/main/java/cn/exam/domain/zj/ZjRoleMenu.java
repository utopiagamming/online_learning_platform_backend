package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_role_menu")
public class ZjRoleMenu implements Serializable {
    /**
     * 主键
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
     * 菜单id
     */
    @Column(name ="menu_id")
    private Integer menuId;
//    /**
//     * 开始时间
//     */
//    @Column(name ="create_time")
//    private String createTime;
//    /**
//     * 结束时间
//     */
//    @Column(name ="update_time")
//    private String updateTime;
}
