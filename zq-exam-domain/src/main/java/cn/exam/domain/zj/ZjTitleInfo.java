package cn.exam.domain.zj;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Data
@Table(name = "zj_title_info")
public class ZjTitleInfo implements Serializable {

     //题目id
    @Id
    @Column(name ="title_id")
    private Integer titleId;

     //题目内容
    @Column(name ="title_name")
    private String titleName;

    //0单选题（a,b,c,d,e,f）  1 填空（0对 1错） 2主观
    @Column(name ="title_status")
    private Integer titleStatus;

     //难度系数
    @Column(name ="title_type")
    private Double titleType;

     //分数
    @Column(name ="title_fraction")
    private Integer titleFraction;

     //答案
    @Column(name ="title_answer")
    private String titleAnswer;

    @Column(name ="choice1")
    private String choice1;

    @Column(name ="choice2")
    private String choice2;

    @Column(name ="choice3")
    private String choice3;

    @Column(name ="choice4")
    private String choice4;

    @Column(name="subject_id")
    private Integer subjectId;

    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "section_id")
    private Integer sectionId;

    // attention:
    // 在此处若使用 Integer[] 为conceptsIds的属性的话，mysql中的json类型转换不过来
    // 除了得自己实现JsonTypeHandler外，还得使用mybatis包下的@Type注解
    // 暂时先改成String——varchar格式
    @Column(name = "concept_ids")
    private String conceptIds;

    @Column(name = "distribution_vector")
    private String distributionVector;

    @Column(name ="create_time")
    private String createTime;

    @Column(name ="update_time")
    private String updateTime;

}
