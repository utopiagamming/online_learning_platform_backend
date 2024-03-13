package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjTitleInfo;
import cn.exam.domain.zj.ZjVideoInfo;
import cn.exam.query.TitlePageQuery;
import cn.exam.query.VideoQuery;
import cn.exam.so.FracAndSecSO;
import cn.exam.vo.TitleConceptVO;
import cn.exam.vo.TitleVO;
import cn.exam.vo.TitleVectorVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjTitleInfoMapper extends CommonBaseMapper<ZjTitleInfo> {

    List<TitleVO> queryPage(TitlePageQuery query);

    TitleVO queryTitleInfo(Integer titleId);

    //在一个难度区间
    List<ZjTitleInfo> queryTitleByDifficulty(@Param("difficulty1") Integer difficulty1,@Param("difficulty2") Integer difficulty2,@Param("subjectId") Integer subjectId);

    List<ZjTitleInfo> queryListByTitleId(@Param("titleIdList") List<Integer> titleIdList);

    List<ZjTitleInfo> queryListByTitleIdE(@Param("titleIdList") List<Integer> titleIdList);

    FracAndSecSO querySectionIdByTitleId(@Param("titleId") Integer titleId);

    List<TitleConceptVO> getAllTitlesWithConcepts(@Param("chapterId")Integer chapterId);

    List<Integer> getLayer2ByLayer1(@Param("layer1Id") Integer layer1Id);

    void updateDistributionVectorByTitleId(@Param("titleId")Integer titleId, @Param("distributionVector")String distributionVector);

    List<TitleVectorVO> getAllTitleAndDistributionVector();

    Integer selectTotalScoreByIdList(List<Integer> titleIdList);

    TitleConceptVO getTitleAndConceptsByTitleId(@Param("titleId") Integer titleId);

    List<ZjVideoInfo> queryAllVideos(VideoQuery query);

    void deleteVideo(@Param("videoId")Integer videoId);

    List<String> getAllHashStrings();

    void insertVideo(@Param("videoInfo")ZjVideoInfo videoInfo);
}
