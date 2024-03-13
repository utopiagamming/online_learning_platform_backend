package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.config.UserUtil;
import cn.exam.dao.mapper.zj.*;
import cn.exam.domain.zj.*;
import cn.exam.query.ErrorQuery;
import cn.exam.service.ErrorDetailService;
import cn.exam.service.PersonalizationService;
import cn.exam.so.SectionCorrectSO;
import cn.exam.util.DateUtil;
import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.CommandDegreeVO;
import cn.exam.vo.ErrorPageVO;
import cn.exam.vo.TitleConceptVO;
import cn.exam.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/personalization")
public class PersonalizationController extends BaseController {
    @Autowired
    private ErrorDetailService errorDetailService;

    @Autowired
    private PersonalizationService personalizationService;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private ZjErrorDetailMapper errorDetailMapper;

    @Autowired
    private ZjCommandDegreeMapper commandDegreeMapper;

    @Autowired
    private ZjPaperInfoMapper paperInfoMapper;

    @Autowired
    private ZjSubjectUserLinkMapper userLinkMapper;

    @Autowired
    private ZjPaperUserMapper paperUserMapper;

    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;

    @Autowired
    private ZjPaperTestMapper paperTestMapper;

    @RequestMapping("/queryErrorPage.htm")
    public void queryErrorPage(HttpServletResponse response, ErrorQuery errorQuery){
        ResultDTO<PageResult<List<ErrorPageVO>>> resultDTO = new ResultDTO<>();
        UserVO userVO= userUtil.getUser();
        errorQuery.setUserId(userVO.getUserId());
        PageResult<List<ErrorPageVO>> listPageResult = errorDetailService.queryErrorPage(errorQuery);

        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }

    @RequestMapping("/showPersonalization.htm")
    public void calcSectionCommandDegree(HttpServletResponse response){
        String userId=userUtil.getUser().getUserId();
        List<SectionCorrectSO> sectionCorrectPercents=errorDetailMapper.selectSectionCorrectPercentSum(userId);
        //System.out.println(sectionCorrectPercents);
        Integer sectionId;
        double commandDegree;
        for(SectionCorrectSO sectionCorrectPercent:sectionCorrectPercents){
            sectionId=sectionCorrectPercent.getSectionId();
            commandDegree=(double)sectionCorrectPercent.getSectionCorrectFraction()/(double)sectionCorrectPercent.getSectionTotalFraction();
            ZjCommandDegree zjCommandDegree=commandDegreeMapper.queryExistenceByUserIdAndSectionId(userId,sectionId);
            if(null==zjCommandDegree){commandDegreeMapper.insertOneCommandDegree(userId,sectionId,commandDegree);}
            else{commandDegreeMapper.updateOneCommandDegree(userId,sectionId,commandDegree);}
        }
        List<CommandDegreeVO> commandDegreeVOS=commandDegreeMapper.queryCommandDegreesByUserId(userId);
        ResultDTO<List<CommandDegreeVO>> resultDTO= new ResultDTO<>();
        resultDTO.setResult(commandDegreeVOS);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO,response);
    }

    @RequestMapping("/submitPersonalization.htm")
    public void createPaperForUser(HttpServletResponse response){
        String userId=userUtil.getUser().getUserId();
        List<CommandDegreeVO> commandDegreeVOS=commandDegreeMapper.queryCommandDegreesByUserId(userId);
    }

    @RequestMapping("createPersonalRecommendationPaper.htm")
    public void createPersonalRecommendationPaper(HttpServletResponse response) {
        String userId=userUtil.getUser().getUserId();
        // 调用getHighRankProblemIds，获得错题id列表
        ArrayList<ArrayList<Integer>> highRankProblemIds = personalizationService.getHighRankProblemIds(userId);

        // 获得所有titleId及其对应vector
        HashMap<Integer, Double[]> allTitleIdAndVectorMap = personalizationService.getAllTitleIdAndVectorMap();

        // 调用recommendByErrorHistoryAndDistributionVector，获得推荐结果
        Set<Integer> recommendedList = personalizationService.recommendByErrorHistoryAndDistributionVector(highRankProblemIds, allTitleIdAndVectorMap);

        // 直观输出推荐效果
        List<TitleConceptVO> oriTitleConceptVOList=new ArrayList<>();
        for(ArrayList<Integer> list:highRankProblemIds){
            for (Integer id:list){
                TitleConceptVO titleConceptVO=titleInfoMapper.getTitleAndConceptsByTitleId(id);
                oriTitleConceptVOList.add(titleConceptVO);
            }
        }
        List<TitleConceptVO> recommendedTitleConceptVOList=new ArrayList<>();
        for (Integer id:recommendedList){
            TitleConceptVO titleConceptVO=titleInfoMapper.getTitleAndConceptsByTitleId(id);
            recommendedTitleConceptVOList.add(titleConceptVO);
        }
        System.out.println("高频错题：");
        for(TitleConceptVO oriTitleConceptVO:oriTitleConceptVOList){
            System.out.println(oriTitleConceptVO.getTitleId()+" "+oriTitleConceptVO.getConceptIds());
        }
        System.out.println("推荐习题：");
        for(TitleConceptVO recTitleConceptVO:recommendedTitleConceptVOList){
            System.out.println(recTitleConceptVO.getTitleId()+" "+recTitleConceptVO.getConceptIds());
        }

        String currentDateTime = DateUtil.getCurrentDateTime();
        // 将推荐写过写入数据库中 注意：多张表
        try {
            // 写入paper_info表内
            ZjPaperInfo paperInfo = new ZjPaperInfo();
            paperInfo.setPaperName(userId + "-个性化试卷-" + currentDateTime);
            paperInfo.setSubjectId(1);
            paperInfo.setDifficulty(4);
            paperInfo.setPersonalization(1);
            paperInfo.setPaperDate("2023-06-01");
            paperInfo.setExamDate("60");
            paperInfo.setPaperNum(recommendedList.size());
            paperInfo.setPaperScore(titleInfoMapper.selectTotalScoreByIdList((new ArrayList<>(recommendedList))));
            paperInfo.setTeachId(userId);
            paperInfo.setCreateTime(currentDateTime);
            paperInfo.setUpdateTime(currentDateTime);
            paperInfoMapper.insertSelective(paperInfo);

            recommendedList.forEach(f -> {
                // 写入subject_user_link表内，即：这张试卷里具体有哪些题目
                ZjSubjectUserLink userLink = new ZjSubjectUserLink();
                userLink.setPaperId(paperInfo.getPaperId());
                userLink.setTitleId(f);
                userLink.setCreateTime(currentDateTime);
                userLink.setUpdateTime(currentDateTime);
                userLinkMapper.insertSelective(userLink);
            });

            // 写入 paper_user表，这里不是班级所有只要给user一个人写
            ZjPaperUser paperUser = new ZjPaperUser();
            paperUser.setPaperId(paperInfo.getPaperId());
            paperUser.setUserId(userId);
            paperUserMapper.insertSelective(paperUser);

            // 写入 paper_test表，同样注意这里只需给一个人写
            List<ZjTitleInfo> zjTitleInfos = titleInfoMapper.queryListByTitleIdE((new ArrayList<>(recommendedList)));
            zjTitleInfos.forEach(g -> {
                ZjPaperTest paperTest = new ZjPaperTest();
                paperTest.setTitleAnswer(g.getTitleAnswer());
                paperTest.setPaperId(paperInfo.getPaperId());
                paperTest.setTitleFraction(g.getTitleFraction());
                paperTest.setTitleId(g.getTitleId());
                paperTest.setUserId(userId);
                paperTest.setCreateTime(DateUtil.getCurrentDateTime());
                paperTestMapper.insertOnePaperTest(paperTest);
            });
            // p.s. 把这几个操作都封装一下呗，写在service里，可以直接复用

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sendJsonSuccess(response);
    }
}
