package cn.exam.dao.mapper.zj;

import cn.exam.dao.mapper.base.CommonBaseMapper;
import cn.exam.domain.zj.ZjPaperInfo;
import cn.exam.query.PaperQuery;
import cn.exam.vo.PaperPageVO;
import cn.exam.vo.PaperTitleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZjPaperInfoMapper extends CommonBaseMapper<ZjPaperInfo> {

    /**
     * 试卷页面
     * @return
     */
    List<PaperPageVO> queryPage(PaperQuery query);

    /**
     * 教师管理员  试卷页面
     * @param query
     * @return
     */
    List<PaperPageVO> queryManagerPage(PaperQuery query);

    List<PaperTitleVO> queryTitlePaper(Integer paperId);
}
