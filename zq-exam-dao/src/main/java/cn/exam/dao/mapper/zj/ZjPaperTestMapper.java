package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjPaperTest;
import cn.exam.vo.PaperTestVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjPaperTestMapper extends CommonBaseMapper<ZjPaperTest> {

    List<PaperTestVO> queryPaperTest(@Param("userId") String userId,@Param("paperId") Integer paperId);

    List<Integer> queryIdByPaperId(Integer paperId);

    List<ZjPaperTest> queryListById(@Param("ids")List<Integer> ids);

    List<ZjPaperTest> queryPaperTestByUserIdAndPaperId(@Param("paperId") Integer paperId, @Param("userId") String userId);

    void insertOnePaperTest(@Param("paperTest") ZjPaperTest zjPaperTest);

    void updateOnePaperTest(@Param("paperTest") ZjPaperTest zjPaperTest);

    Integer queryTitleIdById(@Param("id") Integer id);
}
