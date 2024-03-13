package cn.exam.config;

import cn.exam.RequiredPermission;
import cn.exam.redis.RedisKeyEnum;
import cn.exam.util.Constant;
import cn.exam.util.ExpressException;
import cn.exam.util.LoginErrorException;
import cn.exam.util.SystemCode;
import cn.exam.vo.UserVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckPermissionInterceptor extends HandlerInterceptorAdapter {
    protected static final Log log = LogFactory.getLog(CheckPermissionInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserUtil userUtil;

//    @Autowired
//    private TblUserService userService;

    // 怎么直接使用BaseController里面的函数而不用在这再写一遍呢
    private void sendJson(Object obj, HttpServletResponse response) {
        try {
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("输入json异常", e);
        }
    }

    private void sendJsonError(String description, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("description", description);
        map.put("code", SystemCode.USER_HAVE_NO_PERMISSION);
        sendJson(map, response);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredPermission permissionAnno = handlerMethod.getMethodAnnotation(RequiredPermission.class);
        String exceptUrl = "login.htm;getUserMenuInfo.htm;registerLogin.htm;paperExport.htm;queryExport.htm";
        String[] exceptUrlArray = exceptUrl.split(";");
        for(String url : exceptUrlArray){
            if (requestURI.contains(url)) {
                //没有贴@RequiredPermission注解,可以直接放行通过
                return true;
            }
        }
        if (permissionAnno == null) {
                updateRedisKeyTime();
                return true;
        }else {
            //有贴@RequiredPermission注解,需要校验当前用户是否有权限
            String handlerName = handlerMethod.getBeanType().getName();
            String methodName = handlerMethod.getMethod().getName();
            String btnExpression = handlerName + ":" + methodName;
            System.out.println(btnExpression);
        }
        String btnName = permissionAnno.name();
        sendJsonError("您没有操作权限:" + btnName, response);
        return false;
    }

    private void updateRedisKeyTime() {
        try {
            UserVO user1 = (UserVO)  SecurityUtils.getSubject().getPrincipal();
            if (ObjectUtils.isEmpty(user1)){
                throw new ExpressException(SystemCode.SYSTEM_AGAIN_CODE,"权限失效");
            }
            UserVO user = userUtil.getUser();
            if (user != null) {
                String token = user.getToken();
                String userAndMenuInfo = redisUtil.getKey(RedisKeyEnum.USER.getCode() + ":" + token);
                if (userAndMenuInfo != null) {
                    redisUtil.setKeyTime(RedisKeyEnum.USER.getCode() + ":" + token, userAndMenuInfo, Constant.KEY_IN_REDIS_TIME);
                }
            }
        } catch (LoginErrorException e) {
            throw new ExpressException(SystemCode.USER_LOGIN_ERROR_CODE,"权限失效"+ e);
        }
    }
}
