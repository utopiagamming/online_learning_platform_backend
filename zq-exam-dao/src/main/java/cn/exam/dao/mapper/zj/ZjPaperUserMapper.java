package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjPaperUser;
import cn.exam.query.PaperByUserIdQuery;
import cn.exam.query.PaperUserQuery;
import cn.exam.vo.AchievementExportVO;
import cn.exam.vo.PaperByUserIdVO;
import cn.exam.vo.PaperExportVO;
import cn.exam.vo.PaperUserPage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjPaperUserMapper extends CommonBaseMapper<ZjPaperUser> {

    ZjPaperUser queryPaper(ZjPaperUser paperUser);

    List<PaperUserPage> queryPage(PaperUserQuery query);

    /**
     * 成绩导出
     */
    List<AchievementExportVO> queryExport();

    /**
     * 导出
     */
    List<PaperExportVO> queryPaperExport(Integer paperId);

    /**
     * 查询考完试卷
     *
     * @param query
     */
    List<PaperByUserIdVO> queryPaperByUserId(PaperByUserIdQuery query);

    List<Integer> queryIdByPaperId(Integer paperId);

}
