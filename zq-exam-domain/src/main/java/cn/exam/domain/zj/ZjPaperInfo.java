package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_paper_info")
public class ZjPaperInfo implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name ="paper_id")
    @GeneratedValue(generator = "JDBC")
    private Integer paperId;
    /**
     *
     */
    @Column(name ="paper_name")
    private String paperName;
    /**
     * 科目
     */
    @Column(name ="subject_id")
    private Integer subjectId;
    /**
     * 难度
     */
    @Column(name ="difficulty")
    private Integer difficulty;

    @Column(name = "personalization")
    private Integer personalization;

    /**
     * 班级
     */
    @Column(name ="class_id")
    private Integer classId;

//    /**
//     * 题型
//     */
//    @Column(name ="sub_type")
//    private String subType;

    /**
     * 考试日期
     */
    @Column(name ="paper_date")
    private String paperDate;
    /**
     * 考试时间
     */
    @Column(name ="exam_date")
    private String examDate;
    /**
     * 考试题目
     */
    @Column(name ="paper_num")
    private Integer paperNum;
    /**
     *
     */
    @Column(name ="teach_name")
    private String teachName;
    /**
     *
     */
    @Column(name ="teach_id")
    private String teachId;
    /**
     *
     */
    @Column(name ="create_time")
    private String createTime;
    /**
     *
     */
    @Column(name ="update_time")
    private String updateTime;
    /**
     * 总分
     */
    @Column(name ="paper_score")
    private Integer paperScore;
}
