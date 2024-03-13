package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_class_info")
public class ZjClassInfo implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="class_id")
    private Integer classId;
    /**
     *
     */
    @Column(name ="class_name")
    private String className;
}
