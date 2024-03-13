package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjSubjectUserLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjSubjectUserLinkMapper extends CommonBaseMapper<ZjSubjectUserLink> {
    List<ZjSubjectUserLink> queryByList(Integer paperId);

    boolean deleteByPaperId(@Param("paperId")Integer paperId);

    List<Integer> queryTitleIdsByPaperId(Integer paperId);
}
