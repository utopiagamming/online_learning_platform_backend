package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_chapter_info")
public class ZjChapterInfo implements Serializable {
    @Id
    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "subject_id")
    private Integer subjectId;
}
