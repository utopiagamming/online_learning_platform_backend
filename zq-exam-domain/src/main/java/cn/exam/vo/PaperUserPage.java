package cn.exam.vo;

import lombok.Data;

@Data
public class PaperUserPage {
    private Integer paperId;
    private String paperName;
    private Integer fraction;
    private Integer difficulty;
    private String userName;
    private String className;
    private Integer paperScore;
    private Integer classId;
}
