package cn.exam.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AchievementExportVO {
    @ExcelProperty(value = "试卷名称", index = 0)
    private String paperName;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String userName;
    @ExcelProperty(value = "考试分数", index = 2)
    private Integer fraction;
    @ExcelProperty(value = "试卷难度", index = 3)
    private Integer difficulty;
    @ExcelProperty(value = "班级名称", index = 4)
    private String className;
    @ExcelProperty(value = "试卷总分", index = 5)
    private Integer paperScore;
}
