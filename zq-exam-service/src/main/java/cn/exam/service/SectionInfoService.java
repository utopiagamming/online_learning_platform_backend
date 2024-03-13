package cn.exam.service;

import cn.exam.query.SectionQuery;
import cn.exam.util.PageResult;
import cn.exam.vo.SectionVO;

import java.util.List;

public interface SectionInfoService {
    PageResult<List<SectionVO>> getAllSectionsBySubject(SectionQuery sectionQuery);
}
