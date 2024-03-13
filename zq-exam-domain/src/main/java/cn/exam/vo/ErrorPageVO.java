package cn.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPageVO {
    private String userId;

    private String titleName;

    private Integer titleStatus;

    private String sectionName;

    private Integer answerAccount;

    private Integer correctAccount;

    private Double correctPercent;
}
