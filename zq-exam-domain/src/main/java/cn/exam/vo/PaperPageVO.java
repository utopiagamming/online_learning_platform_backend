package cn.exam.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PaperPageVO {
    private Integer paperId;
    /**
     *
     */
    private String paperName;
    /**
     *
     */
    private Integer subjectId;
    /**
     *
     */
    private String subjectName;
    private Integer difficulty;
    /**
     *
     */
    private String className;
    private Integer classId;

//    /**
//     *
//     */
//    private String subType;

    /**
     *
     */
    private String paperDate;
    /**
     *
     */
    private String examDate;
    /**
     *
     */
    private Integer paperScore;
    /**
     *
     */
    private String teachName;
    /**
     *
     */
    private String teachId;
    /**
     *
     */
    private String createTime;
    /**
     *
     */
    private String updateTime;

    private Integer status;

    private Integer fraction;
}
