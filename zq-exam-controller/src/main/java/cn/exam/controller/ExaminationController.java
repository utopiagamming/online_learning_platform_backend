package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.UserUtil;
import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.domain.zj.*;
import cn.exam.query.PaperByUserIdQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.query.VideoQuery;
import cn.exam.service.ExaminationService;
import cn.exam.service.VideoService;
import cn.exam.util.MD5Utils;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.*;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("title")
public class ExaminationController extends BaseController {

    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private UserUtil userUtil;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;

    // 分页查询库中所有习题
    @RequestMapping("queryTitlePage.htm")
    public void queryTitlePage(HttpServletResponse response, TitlePageQuery query) {
        ResultDTO<PageResult<List<TitleVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<TitleVO>> listPageResult = examinationService.queryPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    // 试卷分页
    @RequestMapping("queryPaperPage.htm")
    public void queryTitlePage(HttpServletResponse response, PaperQuery query) {
        ResultDTO<PageResult<List<PaperPageVO>>> resultDTO = new ResultDTO<>();
        PageResult<List<PaperPageVO>> listPageResult = examinationService.queryManagerPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    @RequestMapping("insertByTitle.htm")
    public void insertByTitle(ZjTitleInfo info, HttpServletResponse response) {
        UserVO user = userUtil.getUser();
        examinationService.insertTitle(info, user);
        sendJsonSuccess(response);
    }

    @RequestMapping("queryTitleInfo.htm")
    public void queryTitleInfo(HttpServletResponse response, Integer titleId) {
        ResultDTO<TitleVO> resultDTO = new ResultDTO<>();
        resultDTO.setResult(examinationService.queryTitleInfo(titleId));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    @RequestMapping("deleteTileInfo.htm")
    public void deleteTileInfo(HttpServletResponse response, Integer titleId) {
        examinationService.deleteTitle(titleId);
        sendJsonSuccess(response);
    }

    @RequestMapping("updateTitle.htm")
    public void updateTitle(HttpServletResponse response,ZjTitleInfo info){
        examinationService.updateTitle(info);
        sendJsonSuccess(response);
    }

    //试卷页面
    @RequestMapping("queryPaper.htm")
    public void queryPaper(Integer paperId,HttpServletResponse response){
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        resultDTO.setResult(examinationService.queryPaper(paperId));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    //考生查看考完的试卷
    @RequestMapping("queryPaperCompleted.htm")
    public void   queryPaperCompleted(Integer paperId,HttpServletResponse response) {
        ResultDTO<PaperTestLevel> resultDTO = new ResultDTO<>();
        UserVO user = userUtil.getUser();
        resultDTO.setResult(examinationService.queryPaperCompleted(paperId,user.getUserId()));
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO, response);
    }

    //组卷功能
    @RequestMapping("audioExam.htm")
    public void audioExam(ZjPaperInfo paperInfo,HttpServletResponse response){
        UserVO user = userUtil.getUser();
        paperInfo.setTeachId(user.getUserId());
        paperInfo.setTeachName(user.getUserName());
        examinationService.audioPaper(paperInfo);
        sendJsonSuccess( response);
    }

    @RequestMapping("updateTitleByList.htm")
    public void updateTitleByList(String titleString,HttpServletResponse response){
        examinationService.updateTitle(titleString);
        sendJsonSuccess(response);
    }

    /**
     * 学生已考试卷查询分页
     */
    @RequestMapping("queryPaperByUserId.htm")
    public void queryPaperByUserId(PaperByUserIdQuery query, HttpServletResponse response){
        ResultDTO<PageResult<List<PaperByUserIdVO>>> resultDTO = new ResultDTO<>();
        UserVO user = userUtil.getUser();
        query.setUserId(user.getUserId());
        PageResult<List<PaperByUserIdVO>> listPageResult = examinationService.queryPaperByUserId(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC, SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO, response);
    }

    @RequestMapping("queryVideoList.htm")
    public void queryVideoList(HttpServletResponse response, VideoQuery videoQuery){
        ResultDTO<PageResult<List<ZjVideoInfo>>> resultDTO=new ResultDTO<>();
        PageResult<List<ZjVideoInfo>> videoList=videoService.queryAllVideoList(videoQuery);
        resultDTO.setResult(videoList);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }

    @RequestMapping("deleteVideo.htm")
    public void deleteVideo(HttpServletResponse response,Integer videoId){
        videoService.deleteVideo(videoId);
        sendJsonSuccess(response);
    }

    public byte[] readMultipartFileToBytes(MultipartFile file) throws IOException{
        byte[] data = new byte[(int) file.getSize()];
        InputStream inputStream = file.getInputStream();
        inputStream.read(data);
        inputStream.close();
        return data;
    }

    @RequestMapping("uploadVideo.htm")
    public void uploadVideo(HttpServletResponse response,VideoVO videoVO) throws IOException {
        // 进行视频对比校验
        //byte[] newBytes=readMultipartFileToBytes(videoVO.getVideoFile());t
        String data=videoVO.getVideoData();
        byte[] newBytes= Base64.decodeBase64(videoVO.getVideoData());
        String newHashString= MD5Utils.videoHashing(newBytes);
        videoVO.setHashString(newHashString);
        boolean flag=true;
        List<String> allHashStrings=titleInfoMapper.getAllHashStrings();
        for(String hashString:allHashStrings){
            if(newHashString.equals(hashString)){
                flag=false;
                break;
            }
        }
        if(flag){
            videoService.uploadVideo(videoVO);
            sendJsonSuccess(response);
        }else{
            sendJsonError(SystemCode.VIDEO_EQUAL_CODE,SystemCode.VIDEO_EQUAL_MSG,response);
        }
    }

}