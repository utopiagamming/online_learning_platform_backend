package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "zj_concept_1_info")
public class ZjConceptLayer1Info {
    @Id
    @Column(name ="zj_concept_1_id")
    private Integer conceptLayer1Id;

    @Column(name ="zj_concept_1_name")
    private String conceptLayer1Name;
}
