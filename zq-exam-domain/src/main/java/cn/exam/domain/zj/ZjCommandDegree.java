package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_command_degree")
public class ZjCommandDegree implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "command_degree")
    private Double commandDegree;

    @Column(name ="create_time")
    private String createTime;

    @Column(name ="update_time")
    private String updateTime;
}
