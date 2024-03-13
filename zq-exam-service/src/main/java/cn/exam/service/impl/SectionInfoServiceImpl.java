package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjSectionInfoMapper;
import cn.exam.query.SectionQuery;
import cn.exam.service.SectionInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.SectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionInfoServiceImpl implements SectionInfoService {
    @Autowired
    ZjSectionInfoMapper zjSectionInfoMapper;

    @Override
    public PageResult<List<SectionVO>> getAllSectionsBySubject(SectionQuery sectionQuery){
        return PageUtil.execute(()->zjSectionInfoMapper.getAllSectionsBySubject(sectionQuery),sectionQuery);
    }
}
