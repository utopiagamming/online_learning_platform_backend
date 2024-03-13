package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.query.ClassQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjClassInfoMapper extends CommonBaseMapper<ZjClassInfo> {

    List<ZjClassInfo> queryPage(ClassQuery query);
}
