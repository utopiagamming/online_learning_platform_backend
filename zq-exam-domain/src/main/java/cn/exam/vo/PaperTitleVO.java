package cn.exam.vo;

import lombok.Data;

@Data
public class PaperTitleVO {
    private Integer paperId;

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
    private Integer fraction;
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
}
