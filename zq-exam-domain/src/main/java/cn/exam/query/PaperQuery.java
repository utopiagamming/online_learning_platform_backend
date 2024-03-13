package cn.exam.query;

import lombok.Data;

@Data
public class PaperQuery extends BaseQuery {

    private Integer classId;
    private String userId;
}
