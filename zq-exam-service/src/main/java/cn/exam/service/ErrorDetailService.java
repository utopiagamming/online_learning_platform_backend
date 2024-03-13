package cn.exam.service;

import cn.exam.query.ErrorQuery;
import cn.exam.util.PageResult;
import cn.exam.vo.ErrorPageVO;

import java.util.List;

public interface ErrorDetailService {
    PageResult<List<ErrorPageVO>> queryErrorPage(ErrorQuery errorQuery);
}
