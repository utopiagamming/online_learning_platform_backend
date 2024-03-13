package cn.exam.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoVO {
    private String videoName;

    private String videoUrl;

    private Integer videoDuration;

    private Double videoSize;

    private Integer sectionId;

    private String videoData;

    private String hashString;
}
