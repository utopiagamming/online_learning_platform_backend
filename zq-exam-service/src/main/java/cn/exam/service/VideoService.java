package cn.exam.service;

import cn.exam.domain.zj.ZjVideoInfo;
import cn.exam.query.VideoQuery;
import cn.exam.util.PageResult;
import cn.exam.vo.VideoVO;

import java.util.List;

public interface VideoService {
    PageResult<List<ZjVideoInfo>> queryAllVideoList(VideoQuery videoQuery);

    void deleteVideo(Integer videoId);

    void uploadVideo(VideoVO videoVO);
}
