package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjSubjectInfoMapper;
import cn.exam.domain.zj.ZjSubjectInfo;
import cn.exam.query.SubjectQuery;
import cn.exam.service.SubjectInfoService;
import cn.exam.util.DateUtil;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {
    @Autowired
    private ZjSubjectInfoMapper subjectInfoMapper;


    @Override
    public void insertSubject(ZjSubjectInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
//        info.setCreateTime(currentDateTime);
//        info.setUpdateTime(currentDateTime);
        subjectInfoMapper.insertSelective(info);

    }

    @Override
    public void updateSubject(ZjSubjectInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
//        info.setCreateTime(currentDateTime);
//        info.setUpdateTime(currentDateTime);
        subjectInfoMapper.updateByPrimaryKeySelective(info);

    }

    @Override
    public void delete(ZjSubjectInfo info) {
        subjectInfoMapper.delete(info);
    }

    @Override
    public PageResult<List<ZjSubjectInfo>> queryPageBySubject(SubjectQuery query) {
        return PageUtil.execute(()->subjectInfoMapper.queryPageBySubject(query),query);
    }

    @Override
    public List<ZjSubjectInfo> queryList() {
        SubjectQuery query = new SubjectQuery();
        return subjectInfoMapper.queryPageBySubject(query);
    }
}
