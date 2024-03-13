package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.domain.zj.ZjSubjectInfo;
import cn.exam.query.SectionQuery;
import cn.exam.query.SubjectQuery;
import cn.exam.service.SectionInfoService;
import cn.exam.service.SubjectInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.ErrorPageVO;
import cn.exam.vo.SectionVO;
import cn.exam.vo.TitleVO;
import cn.exam.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("sub")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectInfoService subjectInfoService;

    @Autowired
    private SectionInfoService sectionInfoService;

    @RequestMapping("queryPageBySubject.htm")
    public void queryPageBySubject(HttpServletResponse response, SubjectQuery query) {
        ResultDTO<PageResult<List<ZjSubjectInfo>>> resultDTO = new ResultDTO<PageResult<List<ZjSubjectInfo>>>();
        PageResult<List<ZjSubjectInfo>> listPageResult = subjectInfoService.queryPageBySubject(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }


    @RequestMapping("updateBySubject.htm")
    public void updateBySubject(HttpServletResponse response, ZjSubjectInfo info) {
        subjectInfoService.updateSubject(info);
        sendJsonSuccess(response);
    }

    @RequestMapping("deleteBySubject.htm")
    public void deleteBySubject(HttpServletResponse response, ZjSubjectInfo info) {
        subjectInfoService.delete(info);
        sendJsonSuccess(response);
    }

    @RequestMapping("insertBySubject.htm")
    public void insertBySubject(HttpServletResponse response, ZjSubjectInfo info) {
        subjectInfoService.insertSubject(info);
        sendJsonSuccess(response);
    }

    /**
     * 科目下拉选
     * @param response
     */
    @RequestMapping("queryListBySub.htm")
    public void queryListBySub(HttpServletResponse response){
        ResultDTO<List<ZjSubjectInfo>> resultDTO = new ResultDTO<List<ZjSubjectInfo>>();
        resultDTO.setResult( subjectInfoService.queryList());
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    @RequestMapping("/getAllSectionsBySubject.htm")
    public void getAllSectionsBySubject(HttpServletResponse response, SectionQuery sectionQuery){
        ResultDTO<PageResult<List<SectionVO>>> resultDTO = new ResultDTO<PageResult<List<SectionVO>>>();
        PageResult<List<SectionVO>> listPageResult = sectionInfoService.getAllSectionsBySubject(sectionQuery);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }
}
