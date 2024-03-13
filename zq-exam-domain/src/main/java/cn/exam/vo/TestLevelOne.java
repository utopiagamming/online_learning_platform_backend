package cn.exam.vo;

import lombok.Data;

@Data
public class TestLevelOne {
    private Integer id;
    /**
     * 题目
     */
    private String titleName;
    /**
     * 分数
     */
    private Integer titleFraction;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String answer;
    //学生答案
    private String studentAnswers;
}
