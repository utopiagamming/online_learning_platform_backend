package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_menu_info")
public class ZjMenuInfo implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="menu_id")
    private Integer menuId;
    /**
     *
     */
    @Column(name ="menu_name")
    private String menuName;
    /**
     * 菜单链接
     */
    @Column(name ="menu_index")
    private String menuIndex;
    /**
     * 层级 0 1(只放两级层级)
     */
    @Column(name ="menu_degree")
    private Integer menuDegree;
    /**
     * 父级id
     */
    @Column(name ="parent_id")
    private Integer parentId;
    /**
     * 0关闭 1启用
     */
    @Column(name ="menu_status")
    private Integer menuStatus;

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
