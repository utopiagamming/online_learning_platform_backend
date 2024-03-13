package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjErrorDetailMapper;
import cn.exam.dao.mapper.zj.ZjPaperTestMapper;
import cn.exam.dao.mapper.zj.ZjPaperUserMapper;
import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.domain.zj.ZjErrorDetail;
import cn.exam.domain.zj.ZjPaperTest;
import cn.exam.domain.zj.ZjPaperUser;
import cn.exam.query.PaperUserQuery;
import cn.exam.service.PaperTestService;
import cn.exam.so.FracAndSecSO;
import cn.exam.so.PaperSuccessSO;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaperTestServiceImpl implements PaperTestService {
    @Autowired
    private ZjPaperTestMapper paperTestMapper;
    @Autowired
    private ZjPaperUserMapper paperUserMapper;

    @Autowired
    private ZjErrorDetailMapper errorDetailMapper;

    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;

    @Override
    public PaperTestLevel paperTest(Integer paperId, String userId) {
        PaperTestLevel testLevel = new PaperTestLevel();
        List<PaperTestVO> paperTestVOS = paperTestMapper.queryPaperTest(userId, paperId);
        PaperTestVO paperTestVO = paperTestVOS.get(0);
        //分析试卷
        List<PaperTestVO> collect = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 0).collect(Collectors.toList());
        List<PaperTestVO> collect1 = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 1).collect(Collectors.toList());
        List<PaperTestVO> collect2 = paperTestVOS.stream().filter(f -> f.getTitleStatus() == 2).collect(Collectors.toList());
        //所有题目总分
        int sum3 = paperTestVOS.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //单选题总分
        int sum = collect.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //填空题总分
        int sum1 = collect1.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        //主观题总分
        int sum2 = collect2.stream().mapToInt(PaperTestVO::getTitleFraction).sum();
        testLevel.setExamDate(paperTestVO.getExamDate());
        testLevel.setPaperName(paperTestVO.getPaperName());
        testLevel.setUserName(paperTestVO.getUserName());
        testLevel.setTotalScore(sum3);
        testLevel.setFractionSum(sum);
        testLevel.setFractionSum1(sum1);
        testLevel.setFractionSum2(sum2);
        testLevel.setTitleNum(collect.size());
        testLevel.setTitleNum1(collect1.size());
        testLevel.setTitleNum2(collect2.size());
        List<TestLevelOne> oneList1 = new ArrayList<>();
        List<TestLevelOne> oneList2 = new ArrayList<>();
        List<TestLevelOne> oneList3 = new ArrayList<>();
        paperTestVOS.forEach(f -> {
            //单选
            if (f.getTitleStatus() == 0) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setTitleFraction(f.getTitleFraction());
                levelOne.setChoice1(f.getChoice1());
                levelOne.setChoice2(f.getChoice2());
                levelOne.setChoice3(f.getChoice3());
                levelOne.setChoice4(f.getChoice4());
                levelOne.setId(f.getId());
                oneList1.add(levelOne);
            } else if (f.getTitleStatus() == 1) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setTitleFraction(f.getTitleFraction());
                levelOne.setId(f.getId());
                oneList2.add(levelOne);
            } else if (f.getTitleStatus() == 2) {
                TestLevelOne levelOne = new TestLevelOne();
                levelOne.setTitleName(f.getTitleName());
                levelOne.setId(f.getId());
                levelOne.setTitleFraction(f.getTitleFraction());
                oneList3.add(levelOne);
            }
        });
        testLevel.setOneList1(oneList1);
        testLevel.setOneList2(oneList2);
        testLevel.setOneList3(oneList3);
        return testLevel;
    }

    @Override
    public List<Integer> queryListIdByPaperId(Integer paperId) {
        return paperTestMapper.queryIdByPaperId(paperId);
    }

    @Override
    public void paperEnd(PaperSuccessSO successSO) {
        String paperList = successSO.getPaperList();
        String str = "[" + paperList + "]";
        JSONArray objects = JSON.parseArray(str);
        List<Integer> ids = new ArrayList<>();
        // 先假设一张卷子只做一次，等改完后再考虑重做卷子怎么记录
        for (Object obj : objects) {
            ZjPaperTest paperTest = new ZjPaperTest();
            JSONObject object = JSON.parseObject(obj.toString());
            Object id = object.get("id");
            if (!ObjectUtils.isEmpty(id)) {
                paperTest.setId(Integer.valueOf(id.toString()));
                ids.add(Integer.valueOf(id.toString()));
            }
            Object val = object.get("val");
            if (!ObjectUtils.isEmpty(val)) { paperTest.setAnswer(val.toString()); }
//            paperTestMapper.updateByPrimaryKeySelective(paperTest);
            paperTestMapper.updateOnePaperTest(paperTest);
        }

        // 计算考生所得总分
        List<ZjPaperTest> paperTests = paperTestMapper.queryListById(ids);
        int fraction = 0;
        String fUserId=successSO.getUserId();
        Integer fTitleId; ZjErrorDetail oriErrorDetail;
        for (ZjPaperTest f : paperTests) {
            String all = f.getTitleAnswer().replaceAll(" ", "");
            if (!ObjectUtils.isEmpty(f.getAnswer())) {
                String all1 = f.getAnswer().replaceAll(" ", "");
                if (all.equals(all1)) {fraction = fraction + f.getTitleFraction();}

                // 更新考生题目错误情况
                fTitleId=paperTestMapper.queryTitleIdById(f.getId());
                oriErrorDetail=errorDetailMapper.queryErrorDetailByUserTitleId(fUserId,fTitleId);
                if(null==oriErrorDetail){
                    ZjErrorDetail errorDetail=new ZjErrorDetail();
                    errorDetail.setUserId(fUserId);
                    errorDetail.setTitleId(fTitleId);
                    FracAndSecSO titleFracAndSection=titleInfoMapper.querySectionIdByTitleId(errorDetail.getTitleId());
                    errorDetail.setTitleFraction(titleFracAndSection.getTitleFraction());
                    errorDetail.setSectionId(titleFracAndSection.getSectionId());
                    errorDetail.setAnswerAccount(1);
                    errorDetail.setCorrectAccount(all.equals(all1)?1:0);
                    errorDetail.setCorrectPercent(((double)errorDetail.getCorrectAccount()/(double)errorDetail.getAnswerAccount()));
                    errorDetailMapper.insertOneErrorDetail(errorDetail);
                }
                else {
                    oriErrorDetail.setAnswerAccount(oriErrorDetail.getAnswerAccount()+1);
                    oriErrorDetail.setCorrectAccount(all.equals(all1)?(oriErrorDetail.getCorrectAccount()+1):oriErrorDetail.getCorrectAccount());
                    double correctPercent=(double)oriErrorDetail.getCorrectAccount()/(double)oriErrorDetail.getAnswerAccount();
                    oriErrorDetail.setCorrectPercent(correctPercent);
                    errorDetailMapper.updateOneErrorDetail(oriErrorDetail);
                }
            }
        }

        // 查询考试人员成绩表
        ZjPaperUser paperUser = new ZjPaperUser();
        paperUser.setPaperId(successSO.getPaperId());
        paperUser.setUserId(successSO.getUserId());
        ZjPaperUser paperUser1 = paperUserMapper.queryPaper(paperUser);

        // 修改考试信息成绩
        paperUser1.setFraction(fraction);
        paperUser1.setStatus(1);
        paperUserMapper.updateByPrimaryKeySelective(paperUser1);
    }

    //List<PaperUserPage> paperUserPageList=paperUserMapper.queryPage(query);

    @Override
    public PageResult<List<PaperUserPage>> queryPaperUser(PaperUserQuery query) {
        return PageUtil.execute(() -> paperUserMapper.queryPage(query), query);
    }

    @Override
    public List<String> queryAchievement(PaperUserQuery query) {
        List<String> strings = new ArrayList<>();
        List<PaperUserPage> paperUserPages = paperUserMapper.queryPage(query);
        List<PaperUserPage> collect = paperUserPages.stream().filter(f -> f.getFraction() != null).collect(Collectors.toList());
        List<PaperUserPage> collect1 = collect.stream().filter(f -> f.getFraction() <= 30).collect(Collectors.toList());
        strings.add(String.valueOf(collect1.size()));
        //大于30 小于等于60
        List<PaperUserPage> collect2 = collect.stream().filter(f -> f.getFraction() > 30).collect(Collectors.toList());
        List<PaperUserPage> collect3 = collect2.stream().filter(f -> f.getFraction() <= 60).collect(Collectors.toList());
        strings.add(String.valueOf(collect3.size()));
        //大于60小于等于80
        List<PaperUserPage> collect4 = collect.stream().filter(f -> f.getFraction() > 60).collect(Collectors.toList());
        List<PaperUserPage> collect5 = collect4.stream().filter(f -> f.getFraction() <= 80).collect(Collectors.toList());
        strings.add(String.valueOf(collect5.size()));
        //100
        List<PaperUserPage> collect6 = collect.stream().filter(f -> f.getFraction() > 80).collect(Collectors.toList());
        List<PaperUserPage> collect7 = collect6.stream().filter(f -> f.getFraction() <= 100).collect(Collectors.toList());
        strings.add(String.valueOf(collect7.size()));
        return strings;
    }

    @Override
    public List<PaperUserQuery> queryClassList() {
        List<PaperUserPage> paperUserPages = paperUserMapper.queryPage( new PaperUserQuery());
        List<PaperUserPage> collect = paperUserPages.stream().filter(f -> f.getFraction() != null).collect(Collectors.toList());
        List<PaperUserQuery> queries = new ArrayList<>();
        collect.forEach(f -> queries.add(PaperUserQuery.builder()
                .classId(f.getClassId())
                .className(f.getClassName())
                .paperId(f.getPaperId())
                .paperName(f.getPaperName())
                .build()));
        return queries;
    }

    @Override
    public List<PaperExportVO> queryPaperExport(Integer paperId) {
        return paperUserMapper.queryPaperExport(paperId);
    }

    @Override
    public List<AchievementExportVO> queryExport() {
        return paperUserMapper.queryExport();

    }
}
