package cn.exam.config;

import cn.exam.redis.RedisKeyEnum;
import cn.exam.util.ExpressException;
import cn.exam.util.LoginErrorException;
import cn.exam.util.SystemCode;
import cn.exam.vo.MenuInfoVO;
import cn.exam.vo.UserMenuInfoVO;
import cn.exam.vo.UserVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Component
public class UserUtil {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取用户及权限
     */
    public UserMenuInfoVO getUserAndPermission(){
        UserMenuInfoVO menuInfoVO = new UserMenuInfoVO();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token=request.getHeader("Authorization");
        if (token==null){
            token=request.getParameter("Authorization");
        }
        if (StringUtils.isEmpty(token)){
            throw new LoginErrorException(SystemCode.TOKEN_ERROR,"token为空");
        }
        System.out.println(token);
        String json = redisUtil.getKey(RedisKeyEnum.USER.getCode() + ":" + token);
        if (StringUtils.isEmpty(json)) {
            throw new LoginErrorException(SystemCode.TOKEN_ERROR,"登录超时");
        }
        UserVO user = JSON.parseObject(json, UserVO.class);
        menuInfoVO.setUser(user);
        return menuInfoVO;
    }

    /**
     * 获取用户
     */
    public UserVO getUser(){
        UserMenuInfoVO userAndPermission = getUserAndPermission();
        if (null == userAndPermission){
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE,"请重新登陆");
        }
        return userAndPermission.getUser();
    }

    /**
     * 获取权限
     */
    public List<MenuInfoVO> getPermission(){
        UserMenuInfoVO userAndPermission = getUserAndPermission();
        if (null == userAndPermission){
            throw new ExpressException(SystemCode.SERVICE_FAILD_CODE,"请重新登陆");
        }
        return userAndPermission.getMenuList();
    }
}
