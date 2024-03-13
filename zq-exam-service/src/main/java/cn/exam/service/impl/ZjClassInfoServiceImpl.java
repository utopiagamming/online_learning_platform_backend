package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjClassInfoMapper;
import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.query.ClassQuery;
import cn.exam.service.ZjClassInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZjClassInfoServiceImpl implements ZjClassInfoService {
    @Autowired
    private ZjClassInfoMapper classInfoMapper;
    @Override
    public PageResult<List<ZjClassInfo>> queryPage(ClassQuery query) {
        return PageUtil.execute(()->classInfoMapper.queryPage(query),query);
    }

    @Override
    public void insert(ZjClassInfo classInfo) {
        classInfoMapper.insert(classInfo);

    }

    @Override
    public void delete(Integer id) {
        classInfoMapper.deleteByPrimaryKey(id);
    }
}
