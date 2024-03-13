package cn.exam.util.recommendation;

import java.util.*;

public class ClusteringUtil {
    public static ArrayList<ArrayList<Integer>> kMeansClustering(int k, HashMap<Integer, Double[]> vectorMap) {
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
        ArrayList<Double[]> centers = new ArrayList<>();

        // 随机初始化质心
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(vectorMap.size());
            Double[] center = vectorMap.get(index);
            centers.add(center);
            clusters.add(new ArrayList<>());
        }

        // 迭代聚类过程，最多迭代100次
        int iterNum = 100;
        while (iterNum > 0) {
            // 清空簇
            for (List<Integer> cluster : clusters) {
                cluster.clear();
            }
            // 将向量分配到最近的簇中
            for (int id : vectorMap.keySet()) {
                Double[] vector = vectorMap.get(id);
                double minDist = Double.MAX_VALUE;
                int nearestClusterIndex = -1;
                for (int i = 0; i < centers.size(); i++) {
                    Double[] center = centers.get(i);
                    double dist = VectorSimilarityCompareUtil.getCompositeSimilarity(vector, center);
                    if (dist < minDist) {
                        minDist = dist;
                        nearestClusterIndex = i;
                    }
                }
                clusters.get(nearestClusterIndex).add(id);
            }
            // 更新质心
            boolean centersUpdated = false;
            for (int i = 0; i < centers.size(); i++) {
                List<Integer> cluster = clusters.get(i);
                if (cluster.size() == 0) {
                    continue;
                }
                Double[] newCenter = new Double[vectorMap.get(1).length];
                for (int j = 0; j < newCenter.length; j++) {
                    double sum = 0.0;
                    for (int id : cluster) {
                        Double[] vector = vectorMap.get(id);
                        sum += vector[j];
                    }
                    newCenter[j] = sum / cluster.size();
                }
                if (!Arrays.equals(newCenter, centers.get(i))) {
                    centers.set(i, newCenter);
                    centersUpdated = true;
                }
            }
            // 如果质心没有更新，则结束迭代
            if (!centersUpdated) {
                break;
            }
            iterNum--;
        }

        return clusters;
    }

    public static int elbowMethod(HashMap<Integer, Double[]> vectorMap, int maxK) {
        ArrayList<Double> wcssList = new ArrayList<>();
        for (int k = 1; k <= maxK; k++) {
            ArrayList<ArrayList<Integer>> clusters = kMeansClustering(k, vectorMap);
            double wcss = 0;
            for (int i = 0; i < k; i++) {
                ArrayList<Integer> cluster = clusters.get(i);
                Double[] center = new Double[vectorMap.get(1).length];
                for (int j = 0; j < center.length; j++) {
                    double sum = 0.0;
                    for (int id : cluster) {
                        Double[] vector = vectorMap.get(id);
                        sum += vector[j];
                    }
                    center[j] = sum / cluster.size();
                }
                for (int id : cluster) {
                    Double[] vector = vectorMap.get(id);
                    wcss += VectorSimilarityCompareUtil.getCompositeSimilarity(vector, center);
                }
            }
            wcssList.add(wcss);
        }
        int elbowIndex = 0;
        double maxDiff = Double.MIN_VALUE;
        for (int i = 1; i < wcssList.size(); i++) {
            double diff = Math.abs(wcssList.get(i) - wcssList.get(i - 1));
            if (diff > maxDiff) {
                maxDiff = diff;
                elbowIndex = i;
            }
        }
        return elbowIndex + 1; // 返回最佳聚类数，即手肘处对应的K值
    }
}
