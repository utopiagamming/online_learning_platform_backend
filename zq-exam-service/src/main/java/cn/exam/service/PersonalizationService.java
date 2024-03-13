package cn.exam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface PersonalizationService {
    // 调用时chapterId恒为12，因为只做了第二章的知识图谱
    HashMap<Integer, Integer[]> getAllTilesWithConceptsOfChapter(Integer chapterId);

    List<Integer> getLayer2ByLayer1(Integer layer1Id);

    ArrayList<ArrayList<Integer>> getHighRankProblemIds(String userId);

    // 获得所有titleId及其对应vector
    HashMap<Integer,Double[]> getAllTitleIdAndVectorMap();

    // 对所有题目的特征向量进行聚类，传回(id+向量)的聚类
    ArrayList<HashMap<Integer,Double[]>> getAllClustering();

    Set<Integer> recommendByErrorHistoryAndDistributionVector(ArrayList<ArrayList<Integer>> allRecommendationsList, HashMap<Integer,Double[]> allTitleIdAndVectorMap);
}
