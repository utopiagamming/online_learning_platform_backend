package cn.exam.vo;

import lombok.Data;

@Data
public class PaperTestVO {
    private Integer id;
    private Integer titleId;
    private String titleName;
    private Integer titleStatus;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private Integer titleFraction;
    private String paperName;
    private Integer examDate;
    private String userName;
}
