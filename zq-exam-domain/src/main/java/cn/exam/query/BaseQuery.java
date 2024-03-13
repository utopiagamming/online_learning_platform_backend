package cn.exam.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseQuery {
    private Integer currentNum = 1;
    private Integer pageSize = 10;
    private String startDate;
    private String endDate;
    private String keyWords;
}
