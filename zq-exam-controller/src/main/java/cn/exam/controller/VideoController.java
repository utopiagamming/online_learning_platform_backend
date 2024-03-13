package cn.exam.controller;


import cn.exam.config.BaseController;
import cn.exam.domain.zj.ZjVideoInfo;
import cn.exam.query.VideoQuery;
import cn.exam.service.VideoService;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("video")
public class VideoController extends BaseController {
    @Autowired
    private VideoService videoService;

    @RequestMapping("queryVideoList.htm")
    public void queryVideoList(HttpServletResponse response, VideoQuery videoQuery){
        ResultDTO<PageResult<List<ZjVideoInfo>>> resultDTO=new ResultDTO<>();
        PageResult<List<ZjVideoInfo>> videoList=videoService.queryAllVideoList(videoQuery);
        resultDTO.setResult(videoList);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }
}
