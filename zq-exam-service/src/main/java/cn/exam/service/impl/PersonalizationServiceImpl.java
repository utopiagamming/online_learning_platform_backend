package cn.exam.service.impl;

import cn.exam.dao.mapper.zj.ZjErrorDetailMapper;
import cn.exam.dao.mapper.zj.ZjTitleInfoMapper;
import cn.exam.service.PersonalizationService;
import cn.exam.util.recommendation.ClusteringUtil;
import cn.exam.util.recommendation.StringUtil;
import cn.exam.util.recommendation.VectorSimilarityCompareUtil;
import cn.exam.vo.ErrorPercentVO;
import cn.exam.vo.TitleConceptVO;
import cn.exam.vo.TitleVectorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalizationServiceImpl implements PersonalizationService {
    @Autowired
    ZjTitleInfoMapper titleInfoMapper;

    @Autowired
    ZjErrorDetailMapper errorDetailMapper;

    @Override
    public HashMap<Integer, Integer[]> getAllTilesWithConceptsOfChapter(Integer chapterId) {
        List<TitleConceptVO> titleConceptVOList= titleInfoMapper.getAllTitlesWithConcepts(chapterId);
        HashMap<Integer,Integer[]> titleAndConceptMap=new HashMap<>();
        for(TitleConceptVO titleConceptVO:titleConceptVOList){
            titleAndConceptMap.put(titleConceptVO.getTitleId(), StringUtil.transStringToIntegerArray(titleConceptVO.getConceptIds()));
        }
        return titleAndConceptMap;
    }

    @Override
    public List<Integer> getLayer2ByLayer1(Integer layer1Id) {
        return titleInfoMapper.getLayer2ByLayer1(layer1Id);
    }

    // 选出排序后错题序列id
    @Override
    public ArrayList<ArrayList<Integer>> getHighRankProblemIds(String userId){
        List<ErrorPercentVO> errorPercentVOS=errorDetailMapper.getTitleAndPercents(userId);
        // 不应该选固定的数目来推荐，而是应该选取错误概率在某一层级之上的
        double r1=0.2, r2=0.5;
        ArrayList<Integer> topRecommendationsList=new ArrayList<>();
        ArrayList<Integer> secondRecommendationsList=new ArrayList<>();
        for(ErrorPercentVO errorPercentVO:errorPercentVOS){
            if(errorPercentVO.getCorrectPercent()<r1){
                topRecommendationsList.add(errorPercentVO.getTitleId());
            }
            else if(errorPercentVO.getCorrectPercent()<r2){
                secondRecommendationsList.add(errorPercentVO.getTitleId());
            }
        }
        ArrayList<ArrayList<Integer>> allRecommendationsList = new ArrayList<>();
        allRecommendationsList.add(topRecommendationsList);
        allRecommendationsList.add(secondRecommendationsList);
        return allRecommendationsList;
    }

    // 获得所有titleId及其对应vector
    @Override
    public HashMap<Integer, Double[]> getAllTitleIdAndVectorMap() {
        List<TitleVectorVO> titleVectorList=titleInfoMapper.getAllTitleAndDistributionVector();
        HashMap<Integer, Double[]> allMap=new HashMap<>();
        for(TitleVectorVO titleVectorVO:titleVectorList){
            allMap.put(titleVectorVO.getTitleId(),StringUtil.transStringToDoubleArray(titleVectorVO.getDistributionVector()));
        }
        return allMap;
    }


    // 对所有题目的特征向量进行聚类，传回(id+向量)的聚类
    @Override
    public ArrayList<HashMap<Integer, Double[]>> getAllClustering() {
        // 获得所有题目id和对应向量
        HashMap<Integer, Double[]> allMap=getAllTitleIdAndVectorMap();
        // 正式进行k-means聚类
        int k=10;
        ArrayList<ArrayList<Integer>> titleIdCluster= ClusteringUtil.kMeansClustering(k,allMap);
        System.out.println(titleIdCluster.stream()
                .map(cluster -> cluster.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", ", "{", "}")))
                .collect(Collectors.joining(", ", "[", "]")));
        return null;
    }

    @Override
    public Set<Integer> recommendByErrorHistoryAndDistributionVector(ArrayList<ArrayList<Integer>> allRecommendationsList,HashMap<Integer,Double[]> allTitleIdAndVectorMap) {
        // 多道题目之间具体如何进行对比：直接对比算法
        List<Integer> mergedTitleIds = allRecommendationsList.stream().flatMap(List::stream).collect(Collectors.toList());
        Set<Integer> allTitleIds=allTitleIdAndVectorMap.keySet();
        List<Integer> remainingList = allTitleIds.stream()
                .filter(i -> !mergedTitleIds.contains(i))
                .collect(Collectors.toList());

        Set<Integer> toBeRecommendedList=new HashSet<>();

        // todo 这里的推荐系数，得动态生成，先取到大小为第十个的值，再把所有小于此值的加入list
        double maxSimilarity=0.0;
        double minSimilarity=5.0;
        HashMap<Integer,Double> similarityMap=new HashMap<>();
        for(ArrayList<Integer> recommendationList:allRecommendationsList){
            for(Integer titleId1:recommendationList){
                for(Integer titleId2:remainingList){
                    double similarity= VectorSimilarityCompareUtil.getCompositeSimilarity(
                            allTitleIdAndVectorMap.get(titleId1),allTitleIdAndVectorMap.get(titleId2));
                    maxSimilarity=Math.max(maxSimilarity,similarity);
                    minSimilarity=Math.min(minSimilarity,similarity);
                    similarityMap.put(titleId2,similarity);
//                    if(similarity<-2.9){  // 3.0则推荐5题 ；2.9则推荐11题 ；2.8则推荐二十几题
//                        toBeRecommendedList.add(titleId2);
//                    }
                }

//                recommendationList= (ArrayList<Integer>) remainingList.stream()
//                        .filter(i -> !toBeRecommendedList.contains(i))
//                        .collect(Collectors.toList());
            }
        }
        LinkedHashMap<Integer, Double> sortedMap = similarityMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        toBeRecommendedList=sortedMap.keySet();

        for(Integer i:toBeRecommendedList) System.out.print(i+", ");
        System.out.println();
        System.out.println("max: "+maxSimilarity);
        System.out.println("min: "+minSimilarity);

        return toBeRecommendedList;

        // 多道题目之间具体如何进行对比：使用聚类的推荐算法
        // 获得所有聚类
        // 判断高频错题分别在哪个聚类中，并在聚类中使用向量对比算法来推荐，获得各个推荐列表
        // 整合所有推荐列表，获得并返回综合结果
    }

}
