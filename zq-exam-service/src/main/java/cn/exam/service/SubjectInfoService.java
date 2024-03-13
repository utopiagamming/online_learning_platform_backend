package cn.exam.service;

import cn.exam.domain.zj.ZjSubjectInfo;
import cn.exam.query.SubjectQuery;
import cn.exam.util.PageResult;

import java.util.List;


public interface SubjectInfoService {

    void  insertSubject(ZjSubjectInfo info);

    void  updateSubject(ZjSubjectInfo info);

    void delete(ZjSubjectInfo info);

    PageResult<List<ZjSubjectInfo>> queryPageBySubject(SubjectQuery query);

    List<ZjSubjectInfo> queryList();

}
