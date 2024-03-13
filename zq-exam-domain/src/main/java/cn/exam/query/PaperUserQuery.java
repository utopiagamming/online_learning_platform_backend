package cn.exam.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaperUserQuery extends BaseQuery {
    private String paperName;
    private String userName;
    private String className;
    private Integer paperId;
    private Integer classId;

}
