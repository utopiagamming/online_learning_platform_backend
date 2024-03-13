package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.domain.zj.ZjVideoInfo;
import cn.exam.query.VideoQuery;
import cn.exam.service.VideoService;
import cn.exam.util.*;
import cn.exam.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;

    @Override
    public PageResult<List<ZjVideoInfo>> queryAllVideoList(VideoQuery videoQuery) {
        return PageUtil.execute(()->titleInfoMapper.queryAllVideos(videoQuery),videoQuery);
    }

    @Override
    public void deleteVideo(Integer videoId) {
        titleInfoMapper.deleteVideo(videoId);
    }

    @Override
    public void uploadVideo(VideoVO videoVO) {
        // 插入到数据库中
        ZjVideoInfo zjVideoInfo=new ZjVideoInfo();
        zjVideoInfo.setVideoUrl(videoVO.getVideoUrl());
        zjVideoInfo.setVideoName(videoVO.getVideoName());
        // 这两个可以由后端自己处理
        zjVideoInfo.setVideoDuration(videoVO.getVideoDuration());
        zjVideoInfo.setVideoSize(videoVO.getVideoSize());
        zjVideoInfo.setSectionId(videoVO.getSectionId());
        zjVideoInfo.setHashString(videoVO.getHashString());
        titleInfoMapper.insertVideo(zjVideoInfo);
        // 存到后端中

    }
}
