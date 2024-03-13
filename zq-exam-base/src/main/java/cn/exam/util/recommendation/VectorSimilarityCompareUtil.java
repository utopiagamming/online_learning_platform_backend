package cn.exam.util.recommendation;

import org.springframework.stereotype.Component;

@Component
public class VectorSimilarityCompareUtil {
    // 余弦相似度计算
    public static double cosineSimilarity(Double[] A, Double[] B) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < A.length; i++) {
            dotProduct += A[i] * B[i];
            normA += A[i] * A[i];
            normB += B[i] * B[i];
        }
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        } else {
            return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        }
    }

    // 欧几里得距离计算
    public static double euclideanDistance(Double[] A, Double[] B) {
        double sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += Math.pow(A[i] - B[i], 2);
        }
        return Math.sqrt(sum);
    }

    // 曼哈顿距离计算
    public static double manhattanDistance(Double[] A, Double[] B) {
        double sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += Math.abs(A[i] - B[i]);
        }
        return sum;
    }

    public static double getCompositeSimilarity(Double[] A, Double[] B){
        if(A==null || B==null) return 0.0;
        // r1：余弦相似度系数 r2：欧几里得距离系数 r3：曼哈顿距离系数
        double r1=0.5, r2=0.18, r3=-0.32;
        return r1*cosineSimilarity(A, B)+r2*euclideanDistance(A, B)+r3*manhattanDistance(A,B);
    }

}
