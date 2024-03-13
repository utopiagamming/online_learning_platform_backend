package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleVO {
    private Integer titleId;
    /**
     * 题目
     */
    private String titleName;
    /**
     * 难度系数
     */
    private Integer titleType;
    /**
     * 分数
     */
    private Integer titleFraction;
    /**
     * 答案
     */
    private String titleAnswer;
    /**
     * 0单选题（a,b,c,d,e,f）  1 填空（0对 1错） 2主观
     */
    private Integer titleStatus;
    /**
     * 选项A
     */
    private String choice1;
    /**
     * 选项B
     */
    private String choice2;
    /**
     * 选项C
     */
    private String choice3;
    /**
     * 选项D
     */
    private String choice4;

//    /**
//     * 出题人id
//     */
//    private String userId;
//    /**
//     * 出题人姓名
//     */
//    private String userName;

    /**
     *
     */
    private String createTime;
    private String subjectName;
    private Integer subjectId;
//    private String className;
}
