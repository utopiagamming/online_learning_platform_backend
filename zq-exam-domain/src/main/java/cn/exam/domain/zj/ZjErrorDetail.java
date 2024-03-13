package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name = "zj_error_detail")
public class ZjErrorDetail {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title_id")
    private Integer titleId;

    @Column(name = "title_fraction")
    private Integer titleFraction;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "answer_account")
    private Integer answerAccount;

    @Column(name = "correct_account")
    private Integer correctAccount;

    @Column(name = "correct_percent")
    private Double correctPercent;

    @Column(name ="create_time")
    private String createTime;

    @Column(name ="update_time")
    private String updateTime;
}
