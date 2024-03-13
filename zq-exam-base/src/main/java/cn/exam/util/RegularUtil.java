package cn.exam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
    private static final String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    public static String outString(String str){
        // 去除中文
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }
    /**
     * 过滤特殊字符
     * @param str 需要过滤的字符串
     */
    public static String stringFilter (String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}

