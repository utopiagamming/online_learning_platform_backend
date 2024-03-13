package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjSubjectInfo;
import cn.exam.query.SubjectQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjSubjectInfoMapper extends CommonBaseMapper<ZjSubjectInfo> {

    List<ZjSubjectInfo> queryPageBySubject(SubjectQuery query);
}
