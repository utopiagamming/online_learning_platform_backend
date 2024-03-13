package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.query.ZjMenuQuery;
import cn.exam.domain.zj.ZjMenuInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjMenuInfoMapper extends CommonBaseMapper<ZjMenuInfo> {

    List<ZjMenuInfo> queryPage(ZjMenuQuery query);

    List<Integer> queryMenuIdListByRoleId(String roleId);
}
