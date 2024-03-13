package cn.exam.util;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanGenerator;

import java.util.Map;

// 没用到

/**
 * 实体工具类
 */
public class GenerateObjectUtil {
    /**
     * 根据属性动态生成对象，并赋值
     * <p>
     * 注意：返回生成的对象属性，均在设置属性前加入前缀$cglib_prop_，例如$cglib_prop_userId
     *
     * @param propertyMap Map<生成的对象变量名称，生成的对象变量值>
     * @return Object
     */
    public static Object generateObjectByField(Map<String, Object> propertyMap) throws Exception {
        BeanGenerator generator = new BeanGenerator();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            generator.addProperty(entry.getKey(), entry.getValue().getClass());
        }
        // 构建对象
        Object obj = generator.create();
        // 赋值
        for (Map.Entry<String, Object> en : propertyMap.entrySet()) {
            BeanUtils.setProperty(obj, en.getKey(), en.getValue());
        }
        return obj;
    }
}
