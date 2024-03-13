package cn.exam.service;

import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.query.ClassQuery;
import cn.exam.util.PageResult;

import java.util.List;

public interface ZjClassInfoService {

    PageResult<List<ZjClassInfo>> queryPage(ClassQuery query);


    void insert(ZjClassInfo classInfo);

    void delete(Integer id);
}
