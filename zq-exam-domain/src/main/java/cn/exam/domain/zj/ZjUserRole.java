package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_user_role")
public class ZjUserRole implements Serializable {
    @Id
    @Column(name ="id")
    private Integer id;

    @Column(name ="user_id")
    private String userId;

    @Column(name ="role_id")
    private String roleId;
}
