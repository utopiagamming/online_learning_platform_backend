package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "zj_concept_2_info")
public class ZjConceptLayer2Info {
    @Id
    @Column(name ="zj_concept_2_id")
    private Integer conceptLayer2Id;

    @Column(name ="zj_concept_2_name")
    private String conceptLayer2Name;
}
