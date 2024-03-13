package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjErrorDetail;
import cn.exam.query.ErrorQuery;
import cn.exam.so.SectionCorrectSO;
import cn.exam.vo.ErrorPageVO;
import cn.exam.vo.ErrorPercentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjErrorDetailMapper extends CommonBaseMapper<ZjErrorDetail> {
    List<ErrorPageVO> queryErrorDetailByQuery(ErrorQuery errorQuery);

    ZjErrorDetail queryErrorDetailByUserTitleId(@Param("userId") String userId,@Param("titleId") Integer titleId);

    void insertOneErrorDetail(@Param("errorDetail") ZjErrorDetail zjErrorDetail);

    void updateOneErrorDetail(@Param("errorDetail") ZjErrorDetail zjErrorDetail);

    List<SectionCorrectSO> selectSectionCorrectPercentSum(@Param("userId") String userId);

    List<ErrorPercentVO> getTitleAndPercents(@Param("userId") String userId);

}
