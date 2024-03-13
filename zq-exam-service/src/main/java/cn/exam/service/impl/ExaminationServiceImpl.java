package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.*;
import cn.exam.domain.zj.*;
import cn.exam.query.PaperByUserIdQuery;
import cn.exam.query.PaperQuery;
import cn.exam.query.TitlePageQuery;
import cn.exam.service.ExaminationService;
import cn.exam.util.*;
import cn.exam.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;
    @Autowired
    private ZjPaperInfoMapper paperInfoMapper;
    @Autowired
    private ZjSubjectUserLinkMapper userLinkMapper;
    @Autowired
    private ZjPaperTestMapper paperTestMapper;
    @Autowired
    private ZjUserInfoMapper userInfoMapper;
    @Autowired
    private ZjPaperUserMapper paperUserMapper;


    @Override
    public PageResult<List<PaperByUserIdVO>> queryPaperByUserId(PaperByUserIdQuery query) {
        return PageUtil.execute(() -> paperUserMapper.queryPaperByUserId(query), query);
    }

    @Override
    public PaperTestLevel queryPaperCompleted(Integer paperId, String userId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        List<PaperTitleVO> paperTitleVOS = paperInfoMapper.queryTitlePaper(paperId);
        List<ZjPaperTest> zjPaperTests = paperTestMapper.queryPaperTestByUserIdAndPaperId(paperId, userId);
        HashMap<Integer,String> map = new HashMap<>();
        zjPaperTests.forEach(f->{
            map.put(f.getTitleId(),f.getAnswer());
        });

        //分析试卷
        List<PaperTitleVO> collect = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTitleVO> collect1 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTitleVO> collect2 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());

        //单选题总分数
        if (!ObjectUtils.isEmpty(collect)) {
            List<TestLevelOne> oneList1 = new ArrayList<>();
            for (PaperTitleVO titleVO : collect) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setChoice1(titleVO.getChoice1());
                levelOne.setChoice2(titleVO.getChoice2());
                levelOne.setChoice3(titleVO.getChoice3());
                levelOne.setChoice4(titleVO.getChoice4());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList1.add(levelOne);
            }
            testLevel.setOneList1(oneList1);
        }
        //填空
        if (!ObjectUtils.isEmpty(collect1)) {
            for (PaperTitleVO titleVO : collect1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList2.add(levelOne);
            }
            testLevel.setOneList2(oneList2);
        }
        //主观
        if (!ObjectUtils.isEmpty(collect2)) {
            for (PaperTitleVO titleVO : collect2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                map.forEach((k,v)->{
                    if (k.equals(titleVO.getTitleId())){
                        levelOne.setStudentAnswers(v);
                    }
                });
                oneList3.add(levelOne);
            }
            testLevel.setOneList3(oneList3);
        }
        return testLevel;
    }

    @Override
    public PageResult<List<PaperPageVO>> queryPage(PaperQuery query) {
        return PageUtil.execute(() -> paperInfoMapper.queryPage(query), query);
    }

    @Override
    public PageResult<List<PaperPageVO>> queryManagerPage(PaperQuery query) {
        return PageUtil.execute(() -> paperInfoMapper.queryManagerPage(query), query);
    }

    @Override
    public PageResult<List<TitleVO>> queryPage(TitlePageQuery query) {
        return PageUtil.execute(() -> titleInfoMapper.queryPage(query), query);
    }

    @Override
    public void insertTitle(ZjTitleInfo info, UserVO user) {
        String currentDateTime = DateUtil.getCurrentDateTime();
//        info.setUserId(user.getUserId());
//        info.setUserName(user.getUserName());
        info.setCreateTime(currentDateTime);
        info.setUpdateTime(currentDateTime);
        titleInfoMapper.insertSelective(info);
    }

    @Override
    public TitleVO queryTitleInfo(Integer titleId) {
        if (titleId == null) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "题目id为空");
        }
        return titleInfoMapper.queryTitleInfo(titleId);
    }

    @Override
    public void deleteTitle(Integer titleId) {
        if (titleId == null) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "题目id为空");
        }
        titleInfoMapper.deleteByPrimaryKey(titleId);
    }

    @Override
    public void updateTitle(ZjTitleInfo info) {
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setUpdateTime(currentDateTime);
        titleInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public PaperTestLevel queryPaper(Integer paperId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        List<PaperTitleVO> paperTitleVOS = paperInfoMapper.queryTitlePaper(paperId);
        //分析试卷
        List<PaperTitleVO> collect = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTitleVO> collect1 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTitleVO> collect2 = paperTitleVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        //单选题总分数
        if (!ObjectUtils.isEmpty(collect)) {
            List<TestLevelOne> oneList1 = new ArrayList<>();
            for (PaperTitleVO titleVO : collect) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setChoice1(titleVO.getChoice1());
                levelOne.setChoice2(titleVO.getChoice2());
                levelOne.setChoice3(titleVO.getChoice3());
                levelOne.setChoice4(titleVO.getChoice4());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList1.add(levelOne);
            }
            testLevel.setOneList1(oneList1);
        }
        //填空
        if (!ObjectUtils.isEmpty(collect1)) {
            for (PaperTitleVO titleVO : collect1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList2.add(levelOne);
            }
            testLevel.setOneList2(oneList2);
        }
        //主观
        if (!ObjectUtils.isEmpty(collect2)) {
            for (PaperTitleVO titleVO : collect2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(titleVO.getTitleName());
                levelOne.setId(titleVO.getTitleId());
                levelOne.setTitleFraction(titleVO.getFraction());
                levelOne.setAnswer(titleVO.getTitleAnswer());
                oneList3.add(levelOne);
            }
            testLevel.setOneList3(oneList3);
        }
        return testLevel;
    }

    /**
     * 组卷
     **/

    @Override
    public void audioPaper(ZjPaperInfo paperInfo) {
        String currentDateTime = DateUtil.getCurrentDateTime();

        List<ZjTitleInfo> zjTitleInfoList = new ArrayList<>();

        // 提出所有难度区间内 该科目的试题
        List<ZjTitleInfo> zjTitleInfos = titleInfoMapper.queryTitleByDifficulty(paperInfo.getDifficulty() - 2, paperInfo.getDifficulty() + 2,paperInfo.getSubjectId());
        int resSum1 = zjTitleInfos.stream().mapToInt(ZjTitleInfo::getTitleFraction).sum();
        if (resSum1 < 100) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "该难度的试题不够(分数不够组卷)");
        }

        // 随机抽出10道单选题
        List<ZjTitleInfo> collect = zjTitleInfos.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        Collections.shuffle(collect);
        if (collect.size() <= 10) {
            collect.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        } else {
            List<ZjTitleInfo> zjTitleInfoList0 = collect.subList(0, 10);
            zjTitleInfoList0.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        }

        // 抽出填空或者主观
        // 需要改一下 改成固定化抽取 4道填空 5道大题

        //  随机抽出4道填空题
        List<ZjTitleInfo> collect1 = zjTitleInfos.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        Collections.shuffle(collect1);
        if (collect1.size() <= 4) {
            collect1.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        } else {
            List<ZjTitleInfo> zjTitleInfoList1 = collect1.subList(0, 4);
            zjTitleInfoList1.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        }

        // 随机抽出5道
        List<ZjTitleInfo> collect2 = zjTitleInfos.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        Collections.shuffle(collect2);
        if (collect2.size() <= 4) {
            collect2.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        } else {
            List<ZjTitleInfo> zjTitleInfoList2 = collect2.subList(0, 5);
            zjTitleInfoList2.forEach(f -> {
                ZjTitleInfo titleInfo = new ZjTitleInfo();
                BeanUtils.copyProperties(f, titleInfo);
                zjTitleInfoList.add(titleInfo);
            });
        }

        // 判断试卷总分有没有到达100
        int sum1 = zjTitleInfoList.stream().mapToInt(ZjTitleInfo::getTitleFraction).sum();
        if (sum1 < 100) {
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "分数不足不能组卷");
        }

        // 写入paper_info表内
        paperInfo.setPersonalization(0);
        paperInfo.setPaperScore(100);
        paperInfo.setCreateTime(currentDateTime);
        paperInfo.setUpdateTime(currentDateTime);
        paperInfoMapper.insertSelective(paperInfo);

        // 写入subject_user_link表内，即：这张试卷里具体有哪些题目
        zjTitleInfoList.forEach(f -> {
            ZjSubjectUserLink userLink = new ZjSubjectUserLink();
            userLink.setClassId(paperInfo.getClassId());
            userLink.setPaperId(paperInfo.getPaperId());
            userLink.setTitleId(f.getTitleId());
            userLink.setCreateTime(currentDateTime);
            userLink.setUpdateTime(currentDateTime);
            userLinkMapper.insertSelective(userLink);
        });

        //组卷完成给这个班级里面的所有学生 生成试卷
        int classId=paperInfo.getClassId();
        List<ZjUserInfo> zjUserInfos = userInfoMapper.queryListByClassId(classId);
        if(null==zjUserInfos||zjUserInfos.size()==0){
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE, "该班级尚未导入学生");
        }
        List<ZjSubjectUserLink> zjSubjectUserLinks = userLinkMapper.queryByList(paperInfo.getPaperId());
        List<Integer> titleIdList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(zjSubjectUserLinks)) {
            zjSubjectUserLinks.forEach(f -> {titleIdList.add(f.getTitleId());});
        }
        ZjSubjectUserLink userLink = zjSubjectUserLinks.get(0);
        List<ZjTitleInfo> zjTitleInfos2 = titleInfoMapper.queryListByTitleIdE(titleIdList);

        // 写入 paper_user表 && paper_test表
        zjUserInfos.forEach(y -> {
            ZjPaperUser paperUser = new ZjPaperUser();
            paperUser.setPaperId(paperInfo.getPaperId());
            paperUser.setUserId(y.getUserId());
            paperUserMapper.insertSelective(paperUser);
            zjTitleInfos2.forEach(f -> {
                ZjPaperTest paperTest = new ZjPaperTest();
                paperTest.setTitleAnswer(f.getTitleAnswer());
                paperTest.setClassId(userLink.getClassId());
                paperTest.setPaperId(paperInfo.getPaperId());
                paperTest.setTitleFraction(f.getTitleFraction());
                paperTest.setTitleId(f.getTitleId());
                paperTest.setUserId(y.getUserId());
                paperTest.setUserName(y.getUserName());
                paperTest.setCreateTime(DateUtil.getCurrentDateTime());
                paperTestMapper.insertOnePaperTest(paperTest);
            });
        });
    }

    @Override
    public void updateTitle(String titleString) {
        String str = "[" + titleString + "]";
        JSONArray objects = JSON.parseArray(str);
        List<Integer> ids = new ArrayList<>();
        for (Object obj : objects) {
            ZjTitleInfo paperTest = new ZjTitleInfo();
            JSONObject object = JSON.parseObject(obj.toString());
            Object id = object.get("id");
            Object answer = object.get("answer");
            if (!ObjectUtils.isEmpty(id)) {
                paperTest.setTitleId(Integer.valueOf(id.toString()));
                ids.add(Integer.valueOf(id.toString()));
            }
            if (!ObjectUtils.isEmpty(answer)) {
                paperTest.setTitleAnswer(answer.toString());
                titleInfoMapper.updateByPrimaryKeySelective(paperTest);
            }
        }
    }
}