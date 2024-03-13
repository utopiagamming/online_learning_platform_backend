package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.UserUtil;
import cn.exam.dao.mapper.zj.ZjPaperInfoMapper;
import cn.exam.dao.mapper.zj.ZjPaperTestMapper;
import cn.exam.dao.mapper.zj.ZjPaperUserMapper;
import cn.exam.dao.mapper.zj.ZjSubjectUserLinkMapper;
import cn.exam.query.PaperQuery;
import cn.exam.query.PaperUserQuery;
import cn.exam.service.ExaminationService;
import cn.exam.service.PaperTestService;
import cn.exam.so.PaperSuccessSO;
import cn.exam.util.*;
import cn.exam.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequestMapping("paper")
public class PaperController extends BaseController {
    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private UserUtil userUtil;

    @Autowired
    private PaperTestService papertestService;
    @Autowired
    private ZjPaperTestMapper paperTestMapper;
    @Autowired
    private ZjPaperInfoMapper paperInfoMapper;

    @Autowired
    private ZjPaperUserMapper paperUserMapper;

    @Autowired
    private ZjSubjectUserLinkMapper subjectUserLinkMapper;

    /**
     * 学生考试页面1
     * @param response httpResponse
     * @param query paperQuery
     */
    @RequestMapping("queryPaperPage.htm")
    public void queryTitlePage(HttpServletResponse response, PaperQuery query) {
        ResultDTO<PageResult<List<PaperPageVO>>> resultDTO = new ResultDTO<>();
        UserVO user = userUtil.getUser();
        Integer classId = user.getClassId();
        query.setClassId(classId);
        query.setUserId(user.getUserId());
        PageResult<List<PaperPageVO>> listPageResult = examinationService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    /**
     * 点击考试触发按钮
     */
    @RequestMapping("addPaperByStudent.htm")
    public void addPaperByStudent(Integer paperId,HttpServletResponse response){
        UserVO user = userUtil.getUser();
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        resultDTO.setResult(papertestService.paperTest(paperId, user.getUserId()));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    /**
     * 删除试卷
     */
    @RequestMapping("deletePaper.htm")
    public void deletePaper(Integer paperId,HttpServletResponse response){
        List<Integer> integers = papertestService.queryListIdByPaperId(paperId);
        integers.forEach(f-> paperTestMapper.deleteByPrimaryKey(f));
        List<Integer> integers2=paperUserMapper.queryIdByPaperId(paperId);
        integers2.forEach(g->paperUserMapper.deleteByPrimaryKey(g));
        subjectUserLinkMapper.deleteByPaperId(paperId);
        paperInfoMapper.deleteByPrimaryKey(paperId);
        sendJsonSuccess(response);
    }

    /**
     * 考试提交页面
     * @param successSO paperSuccessSO
     * @param response httpServletResponse
     */
    @RequestMapping("successPaper.htm")
    public void  successPaper(PaperSuccessSO successSO , HttpServletResponse response){
        UserVO user = userUtil.getUser();
        successSO.setUserId(user.getUserId());
        papertestService.paperEnd(successSO);
        sendJsonSuccess(response);
    }

    /**
     * 成绩查询页面
     */
    @RequestMapping("queryPage.htm")
    public void queryPage(PaperUserQuery query ,HttpServletResponse response){
        ResultDTO<PageResult<List<PaperUserPage>>> resultDTO = new ResultDTO<>();
        PageResult<List<PaperUserPage>> listPageResult = papertestService.queryPaperUser(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }


    @RequestMapping("queryAchievement.htm")
    public void queryAchieve(HttpServletResponse response,PaperUserQuery query){
        ResultDTO<List<String> > resultDTO = new ResultDTO<>();
        resultDTO.setResult(papertestService.queryAchievement(query));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    /**
     * 班级下拉选
     * @param response httpServletResponse
     */
    @RequestMapping("queryClassIdList.htm")
    public void queryClassIdList(HttpServletResponse response){
        ResultDTO<List<PaperUserQuery> > resultDTO = new ResultDTO<>();
        List<PaperUserQuery> queries = papertestService.queryClassList();
        ArrayList<PaperUserQuery> infos2 =
                queries.stream()
                        .collect(Collectors.collectingAndThen(Collectors
                                .toCollection(() -> new TreeSet<>(Comparator
                                        .comparing(PaperUserQuery::getClassId))), ArrayList::new));
        resultDTO.setResult(infos2);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    /**
     * 试卷下拉选和班级下拉选
     * @param response httpServletResponse
     */
    @RequestMapping("queryPaperIdList.htm")
    public void queryPaperIdList(HttpServletResponse response){
        ResultDTO<List<PaperUserQuery> > resultDTO = new ResultDTO<>();
        List<PaperUserQuery> queries = papertestService.queryClassList();
        ArrayList<PaperUserQuery> infos2 =
                queries.stream()
                        .collect(Collectors.collectingAndThen(Collectors
                                .toCollection(() -> new TreeSet<>(Comparator
                                        .comparing(PaperUserQuery::getPaperId))), ArrayList::new));
        resultDTO.setResult(infos2);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    @RequestMapping("paperExport.htm")
    public void paperExport(Integer paperId,HttpServletResponse response) throws IOException {
        List<PaperExportVO> paperExportVOS = papertestService.queryPaperExport(paperId);
        PaperExportVO paperExportVO = paperExportVOS.get(0);
        String fileName = paperExportVO.getPaperName()+".xlsx";
        EasyExcelUtil.writeWeb(fileName, PaperExportVO.class, paperExportVOS, response);
    }

    @RequestMapping("queryExport.htm")
    public void queryExport(HttpServletResponse response) throws IOException {
        List<AchievementExportVO> achievementExportVOS = papertestService.queryExport();
        String fileName = "成绩单.xlsx";
        EasyExcelUtil.writeWeb(fileName, AchievementExportVO.class, achievementExportVOS, response);
    }

}
