package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "zj_section_info")
public class ZjSectionInfo implements Serializable {
    @Id
    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "subject_id")
    private Integer subjectId;
}
