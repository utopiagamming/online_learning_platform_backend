package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjSectionInfo;
import cn.exam.query.SectionQuery;
import cn.exam.so.ChapterAndSectionsSO;
import cn.exam.so.ChapterSO;
import cn.exam.vo.ErrorPercentVO;
import cn.exam.vo.SectionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjSectionInfoMapper extends CommonBaseMapper<ZjSectionInfo> {
    List<SectionVO> getAllSectionsBySubject(SectionQuery sectionQuery);

    List<ChapterSO> getAllChapterBySubjectId(@Param("subjectId")Integer subjectId);

    List<ChapterAndSectionsSO> getAllSectionByChapterId(@Param("subjectId")Integer subjectId);
}
