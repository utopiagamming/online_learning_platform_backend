package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.dao.mapper.zj.ZjClassInfoMapper;
import cn.exam.domain.zj.ZjClassInfo;
import cn.exam.domain.zj.ZjSubjectInfo;
import cn.exam.query.ClassQuery;
import cn.exam.service.ZjClassInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("class")
public class ZjClassController extends BaseController {
    @Autowired
    private ZjClassInfoService classInfoService;
    @Autowired
    private ZjClassInfoMapper classInfoMapper;

    @RequestMapping("queryPageByClass.htm")
    public void queryPageByClass(ClassQuery query , HttpServletResponse response){
        ResultDTO<PageResult<List<ZjClassInfo>>> resultDTO = new ResultDTO<PageResult<List<ZjClassInfo>>>();
        PageResult<List<ZjClassInfo>> listPageResult = classInfoService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    @RequestMapping("queryList.htm")
    public void queryList(HttpServletResponse response){
        ResultDTO<List<ZjClassInfo>> resultDTO = new ResultDTO<List<ZjClassInfo>>();
        resultDTO.setResult( classInfoMapper.selectAll());
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    @RequestMapping("insert.htm")
    public void insert(ZjClassInfo classInfo, HttpServletResponse response){
        classInfoService.insert(classInfo);
        sendJsonSuccess(response);
    }

    @RequestMapping("delete.htm")
    public void delete(Integer id , HttpServletResponse response){
        classInfoService.delete(id);
        sendJsonSuccess(response);
    }

}

