package cn.exam.service;

import cn.exam.domain.zj.ZjPaperInfo;
import cn.exam.domain.zj.ZjTitleInfo;
import cn.exam.query.PaperByUserIdQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.*;

import java.util.List;


public interface ExaminationService {

    //试卷管理分页
    PageResult<List<PaperPageVO>> queryPage (PaperQuery query);

    //试卷管理分页
    PageResult<List<PaperPageVO>> queryManagerPage (PaperQuery query);


    PageResult<List<TitleVO>> queryPage(TitlePageQuery query);

    void insertTitle(ZjTitleInfo info, UserVO user);

    TitleVO queryTitleInfo(Integer titleId);

    void deleteTitle(Integer titleId);

    void updateTitle(ZjTitleInfo info);

    //试卷页面
    PaperTestLevel queryPaper(Integer paperId);

    //查看已考试卷
    PaperTestLevel queryPaperCompleted(Integer paperId,String userId);
    //自动组卷
    void audioPaper(ZjPaperInfo paperInfo);

    //修改试题
    void updateTitle(String titleString);

    PageResult<List<PaperByUserIdVO>> queryPaperByUserId(PaperByUserIdQuery query);

}
