package cn.exam.controller;

import cn.exam.dao.mapper.zj.ZjSubjectUserLinkMapper;
import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.enums.SuccessorMap;
import cn.exam.service.PersonalizationService;
import cn.exam.util.MD5Utils;
import cn.exam.util.recommendation.StringUtil;
import cn.exam.vo.TitleConceptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;

@Controller
public class DistributionVectorCalcController {
    @Autowired
    private PersonalizationService personalizationService;

    @Autowired
    private ZjTitleInfoMapper titleInfoMapper;

    @Autowired
    private ZjSubjectUserLinkMapper subjectUserLinkMapper;

    public Double[] getDistributionVector(Integer[] conceptIds){
        Double[] disVector=new Double[SuccessorMap.vectorLength];
        Arrays.fill(disVector,0.0);
        // 系数 r11：用于layer1级乘；r12：从layer1跳到layer2；r2：layer2级乘
        double r11=0.85, r12=0.8, r2=0.95;
        ArrayList<Integer> layer1=new ArrayList<>();
        ArrayList<Integer> layer2=new ArrayList<>();
        // layer2、layer1初始化
        for(Integer conceptId:conceptIds){
            if(conceptId>10000) layer2.add(conceptId);
            else layer1.add(conceptId);
        }
        // 写入disVector的值，随while循环轮轮更新
        double value2=1.0, value1=r12;
        // 由已存在的layer2来写入disVector
        System.out.println(SuccessorMap.indexMap);
        while(!layer2.isEmpty()){
            ArrayList<Integer> newLayer2=new ArrayList<>();
            for(Integer id2:layer2){
                int index2=(int) SuccessorMap.indexMap.get(id2);
                disVector[index2]=Math.max(disVector[(int) SuccessorMap.indexMap.get(id2)],value2);
                if(SuccessorMap.BE_SUCCESSOR_TO_MAP.containsKey(id2)) {
                    newLayer2.addAll(Arrays.asList(SuccessorMap.BE_SUCCESSOR_TO_MAP.get(id2)));
                }
            }
            value2*=r2;
            layer2=newLayer2;
        }
        // 由已存在的layer1来写入disVector
        // 此时 layer2 已经清空，可以重新使用
        while(!layer1.isEmpty()){
            ArrayList<Integer> newLayer1=new ArrayList<>();
            ArrayList<Integer> newLayer2=new ArrayList<>();
            for(Integer id1:layer1){
                if(SuccessorMap.BE_SUCCESSOR_TO_MAP.containsKey(id1)) {
                    newLayer1.addAll(Arrays.asList(SuccessorMap.BE_SUCCESSOR_TO_MAP.get(id1)));
                }
                List<Integer> id2List=personalizationService.getLayer2ByLayer1(id1);
                for(Integer id2:newLayer2){
                    int index2=(int) SuccessorMap.indexMap.get(id2);
                    disVector[index2]=Math.max(disVector[(int) SuccessorMap.indexMap.get(id2)],value1);
                }
                newLayer2.addAll(id2List);
            }
            value1*=r11;
            layer1=newLayer1;
        }

        return disVector;
    }

    public void calcAndWriteDistributionVector(){
        // 1 获得第二章所有题目 id + conceptIds
        HashMap<Integer, Integer[]> titleAndConceptMap=personalizationService.getAllTilesWithConceptsOfChapter(12);
        // 2 使用 BE_SUCCESSOR_TO_MAP 把每道题目都拍成一维向量
        for (Map.Entry<Integer, Integer[]> entry:titleAndConceptMap.entrySet()){
            Integer[] conceptIds=entry.getValue();
            String disVectorString= StringUtil.transDoubleArrayToString(getDistributionVector(conceptIds));
            // 3 直接更新到数据库里去
            titleInfoMapper.updateDistributionVectorByTitleId(entry.getKey(),disVectorString);
        }
    }

    public void calcClusterAndFindBestMatchingK(){
        personalizationService.getAllClustering();
    }

    public void showRecommendResult(String userId,Integer paperId){
        ArrayList<ArrayList<Integer>> highWrongProblems=personalizationService.getHighRankProblemIds(userId);
        List<Integer> highWrongProblemsList=new ArrayList<>();
        for(ArrayList<Integer> list:highWrongProblems){
            highWrongProblemsList.addAll(list);
        }
        List<TitleConceptVO> oriTitleConceptVOList=new ArrayList<>();
        for(Integer oriTitle:highWrongProblemsList){
            TitleConceptVO titleConceptVO=titleInfoMapper.getTitleAndConceptsByTitleId(oriTitle);
            oriTitleConceptVOList.add(titleConceptVO);
        }

        List<Integer> recommendedProblemsList=subjectUserLinkMapper.queryTitleIdsByPaperId(paperId);
        List<TitleConceptVO> recommendedTitleConceptVOList=new ArrayList<>();
        for(Integer oriTitle:recommendedProblemsList){
            TitleConceptVO titleConceptVO=titleInfoMapper.getTitleAndConceptsByTitleId(oriTitle);
            recommendedTitleConceptVOList.add(titleConceptVO);
        }
        System.out.println("ori problems:");
        for(TitleConceptVO oriTitleConceptVO:oriTitleConceptVOList){
            System.out.println(oriTitleConceptVO.getTitleId()+" "+oriTitleConceptVO.getConceptIds());
        }
        System.out.println("recommended problems:");
        for(TitleConceptVO recTitleConceptVO:recommendedTitleConceptVOList){
            System.out.println(recTitleConceptVO.getTitleId()+" "+recTitleConceptVO.getConceptIds());
        }
    }

    public void videoHashingTest(){
        String filePathPrefix="/Users/mirabilite/IdeaProjects/version1/zj-exam/zq-exam-controller/src/main/java/cn/exam/";
//        String filePath1="/Users/mirabilite/IdeaProjects/version1/zj-exam/zq-exam-controller/src/main/java/cn/exam/12111 进位计数法-28.mp4";
//        String filePath2="/Users/mirabilite/IdeaProjects/version1/zj-exam/zq-exam-controller/src/main/java/cn/exam/12112 进制转换-28.mp4";
//        String filePath3="/Users/mirabilite/IdeaProjects/version1/zj-exam/zq-exam-controller/src/main/java/cn/exam/12141 字符-28.mp4";
        String[] filePathSuffixes={
            "12111 进位计数法-28.mp4","12112 进制转换-28.mp4","1213 BCD码-28.mp4","12141 字符-28.mp4",
            "12151 奇偶校验码-28.mp4","12152 海明码-28.mp4","12153 循环校验码-28.mp4"
        };
        try {
            for(String suffix:filePathSuffixes){
                byte[] bytes=MD5Utils.readVideoFile(filePathPrefix+suffix);
                System.out.println(MD5Utils.videoHashing(bytes));
            }
//            byte[] bytes1 = MD5Utils.readVideoFile(filePath1);
//            System.out.println(filePath1+": MD5哈希值 "+MD5Utils.videoHashing(bytes1));
//            byte[] bytes2 = MD5Utils.readVideoFile(filePath2);
//            System.out.println(filePath2+": MD5哈希值 "+MD5Utils.videoHashing(bytes2));
//            byte[] bytes3 = MD5Utils.readVideoFile(filePath3);
//            System.out.println(filePath3+": MD5哈希值 "+MD5Utils.videoHashing(bytes3));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
