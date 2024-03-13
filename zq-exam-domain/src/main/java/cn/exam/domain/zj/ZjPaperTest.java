package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_paper_test")
public class ZjPaperTest implements Serializable {
    /**
     *
     */
    @Id
    @Column(name ="id")
    private Integer id;
    /**
     *
     */
    @Column(name ="class_id")
    private Integer classId;
    /**
     *
     */
    @Column(name ="paper_id")
    private Integer paperId;
    /**
     *
     */
    @Column(name ="title_id")
    private Integer titleId;
    /**
     * 标准答案
     */
    @Column(name ="title_answer")
    private String titleAnswer;
    /**
     * 学生答案
     */
    @Column(name ="answer")
    private String answer;
    /**
     * 本题分数
     */
    @Column(name ="title_fraction")
    private Integer titleFraction;
    /**
     *
     */
    @Column(name ="user_id")
    private String userId;
    /**
     *
     */
    @Column(name ="user_name")
    private String userName;
    /**
     *
     */
    @Column(name ="create_time")
    private String createTime;
}
