package cn.exam.enums;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SuccessorMap {
    public static final Integer[] CONCEPT_LAYER_1=new Integer[]{
            1211,1212,1213,1214,1215, // 121
            1221,1222,1223,1224,      // 122
            1231,1232,                // 123
            1241,1242                 // 124
    };

    public static final int vectorLength=34;

    public static final Integer[] CONCEPT_LAYER_2=new Integer[]{
            12111,12112,  12141,12142,  12151,12152,12153,  // 1212、1213无 ConceptLayer2
            12211,12212,12213,  12221,12222,12223,12224,12225,12226,12227,  12231,12232  ,12241,12242,
            12311,12312,12313,12314,  12321,12322,12323,12324,12325,12326,
            12411,12412,12413                               // 1242无 ConceptLayer2
    };

    public static Map<Object, Object> indexMap = new HashMap<>();
    public static final Map<Integer,Integer[]> BE_SUCCESSOR_TO_MAP = new HashMap<>();

    public static void initializing(){
        // BE_SUCCESSOR_TO_MAP 初始化

        // ConceptLayer1 间依赖关系
        // chap 2
        BE_SUCCESSOR_TO_MAP.put(1212,new Integer[]{1211});
        BE_SUCCESSOR_TO_MAP.put(1213,new Integer[]{1211}); // new
        BE_SUCCESSOR_TO_MAP.put(1222,new Integer[]{1211});
        BE_SUCCESSOR_TO_MAP.put(1232,new Integer[]{1231});
        BE_SUCCESSOR_TO_MAP.put(1242,new Integer[]{1241});

        // ConceptLayer2 间依赖关系
        // chap 2
        BE_SUCCESSOR_TO_MAP.put(12112,new Integer[]{12111});
        BE_SUCCESSOR_TO_MAP.put(12213,new Integer[]{12111,12112});
        BE_SUCCESSOR_TO_MAP.put(12222,new Integer[]{12221});
        BE_SUCCESSOR_TO_MAP.put(12223,new Integer[]{12221,12222});
        BE_SUCCESSOR_TO_MAP.put(12225,new Integer[]{12221});
        BE_SUCCESSOR_TO_MAP.put(12226,new Integer[]{12221,12222,12223});
        BE_SUCCESSOR_TO_MAP.put(12227,new Integer[]{12221,12222});
        BE_SUCCESSOR_TO_MAP.put(12312,new Integer[]{12311});
        BE_SUCCESSOR_TO_MAP.put(12313,new Integer[]{12311,12312});
        BE_SUCCESSOR_TO_MAP.put(12322,new Integer[]{12321});
        BE_SUCCESSOR_TO_MAP.put(12323,new Integer[]{12322});
        BE_SUCCESSOR_TO_MAP.put(12324,new Integer[]{12323});
        BE_SUCCESSOR_TO_MAP.put(12325,new Integer[]{12324});
        BE_SUCCESSOR_TO_MAP.put(12326,new Integer[]{12311,12325}); //new
        BE_SUCCESSOR_TO_MAP.put(12412,new Integer[]{12411});
        BE_SUCCESSOR_TO_MAP.put(12413,new Integer[]{12411});

        // indexMap 初始化
        indexMap= IntStream.range(0, CONCEPT_LAYER_2.length).boxed()
                .collect(Collectors.toMap(i -> CONCEPT_LAYER_2[i], i -> i));
    }
}
