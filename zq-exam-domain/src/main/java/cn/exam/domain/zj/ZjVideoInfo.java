package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_video_info")
public class ZjVideoInfo implements Serializable {
    @Id
    @Column(name = "video_id")
    private Integer videoId;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "video_duration")
    private Integer videoDuration;

    @Column(name = "video_size")
    private Double videoSize;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "hash_string")
    private String hashString;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;
}
