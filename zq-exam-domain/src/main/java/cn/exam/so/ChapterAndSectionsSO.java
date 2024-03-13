package cn.exam.so;

import lombok.Data;

import java.util.List;

@Data
public class ChapterAndSectionsSO {
    private Integer chapterId;
    private String chapterName;
    private List<SectionSO> sectionList;
}
