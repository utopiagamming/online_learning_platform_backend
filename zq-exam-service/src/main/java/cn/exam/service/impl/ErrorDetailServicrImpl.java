package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjErrorDetailMapper;
import cn.exam.query.ErrorQuery;
import cn.exam.service.ErrorDetailService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.ErrorPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorDetailServicrImpl implements ErrorDetailService {
    @Autowired
    private ZjErrorDetailMapper zjErrorDetailMapper;

    @Override
    public PageResult<List<ErrorPageVO>> queryErrorPage(ErrorQuery errorQuery){
        return PageUtil.execute(()->zjErrorDetailMapper.queryErrorDetailByQuery(errorQuery),errorQuery);
    }
}
