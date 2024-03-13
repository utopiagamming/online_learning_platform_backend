package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamPaperVO {
    private String title;
    private Integer fraction;
    private Integer type;
    private Integer id;
    private List<PaperVO> list;

}
