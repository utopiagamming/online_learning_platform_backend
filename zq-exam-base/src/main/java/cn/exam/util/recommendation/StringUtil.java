package cn.exam.util.recommendation;

import org.springframework.stereotype.Component;

import java.util.Arrays;

// 处理字符串
@Component
public class StringUtil {
    // 输入字符串变成Integer数组
    public static Integer[] transStringToIntegerArray(String inputStr){
        int[] intArray= Arrays.stream(inputStr.replaceAll("\\[|]|\\s", "").split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
        return Arrays.stream(intArray).boxed().toArray(Integer[]::new);
    }

    public static String transDoubleArrayToString(Double[] doubleArray){
        String doubleString=Arrays.toString(doubleArray);
        return doubleString.substring(1,doubleString.length()-1);
    }

    // 输入的字符串变成Double数组
    public static Double[] transStringToDoubleArray(String inputStr){
        double[] doubleArray= Arrays.stream(inputStr.replaceAll("\\[|]|\\s", "").split(","))
                .map(String::trim)
                .mapToDouble(Double::parseDouble)
                .toArray();
        return Arrays.stream(doubleArray).boxed().toArray(Double[]::new);
    }
}
