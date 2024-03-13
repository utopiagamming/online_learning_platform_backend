package cn.exam.query;

import lombok.Data;

@Data
public class PaperByUserIdQuery extends BaseQuery{
    private String userId;
}
