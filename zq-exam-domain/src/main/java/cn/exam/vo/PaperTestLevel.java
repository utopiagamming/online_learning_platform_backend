package cn.exam.vo;

import lombok.Data;

import java.util.List;

@Data
public class PaperTestLevel {
    /**
     * 试卷总分
     */
    private Integer totalScore;
    /**
     * 试卷名
     */
    private String paperName;
    /**
     * 考试时间
     */
    private Integer examDate;
    /**
     * 考试人名
     */
    private String userName;
    //单选
    /**
     * 题目数量
     */
    private Integer titleNum;
    /**
     * 题目总分
     */
    private Integer fractionSum;
    /**
     * 试题
     */
    private List<TestLevelOne> oneList1;

    //填空
    /**
     * 题目数量
     */
    private Integer titleNum1;
    /**
     * 题目总分
     */
    private Integer fractionSum1;
    /**
     * 试题
     */
    private List<TestLevelOne> oneList2;


    //主观
    /**
     * 题目数量
     */
    private Integer titleNum2;
    /**
     * 题目总分
     */
    private Integer fractionSum2;
    /**
     * 试题
     */
    private List<TestLevelOne> oneList3;
    
}
