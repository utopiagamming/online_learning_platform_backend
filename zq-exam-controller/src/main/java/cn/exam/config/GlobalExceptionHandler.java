package cn.exam.config;

import cn.exam.util.ExpressException;
import cn.exam.util.SystemCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController implements Serializable{
    private static final long serialVersionUID = 103186762185178610L;

    /**
     * 参数校验异常
     * @param exception 异常对象
     */
    @ExceptionHandler(value={BindException.class})
    public void BindException(BindException exception,HttpServletResponse response)
    {
        exception.printStackTrace();
        StringBuffer sb=new StringBuffer();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            sb.append(error.getDefaultMessage());
            sb.append("/");
        }
        if (StringUtils.isEmpty(sb.toString())){
            sb.append(exception.getBindingResult().getObjectName());
        }
        sendJsonError(SystemCode.SYS_EXCEPTION_CODE,String.valueOf(sb),response);
//        sendJsonError(Result.builder().code("10001").description(String.valueOf(sb)).build(),response);
    }
    /**
     * 参数校验异常
     * @param exception 异常对象
     */
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public void MethodArgumentNotValidException(MethodArgumentNotValidException exception,HttpServletResponse response) {
        exception.printStackTrace();
        StringBuffer sb=new StringBuffer();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            sb.append(error.getDefaultMessage());
            sb.append("/");
        }
        if (StringUtils.isEmpty(sb.toString())){
            sb.append(exception.getBindingResult().getObjectName());
        }
        sendJsonError(SystemCode.SYS_EXCEPTION_CODE,String.valueOf(sb),response);
    }

    /**
     * ArbexpressException,自定义异常
     * @param exception 异常对象
     */
    @ExceptionHandler(value={ExpressException.class})
    public void ExpressException(ExpressException exception, HttpServletResponse response) {
        log.error(exception.errMsg,exception);
        sendJsonError(exception.getErrCode(),exception.getErrMsg(),response);
    }
//
    @ExceptionHandler(value = UnauthorizedException.class)
    public void UnauthorizedException(UnauthorizedException exception,HttpServletResponse response){
        log.info("权限认证异常:"+ JSON.toJSONString(exception));
        sendJsonError(SystemCode.USER_LOGIN_ERROR_CODE,SystemCode.USER_LOGIN_ERROR_MSG,response);
    }

    /**
     * @param exception 异常对象
     * @param response
     * 登录用户名或密码错误异常
     */
    @ExceptionHandler(value={AuthenticationException.class})
    public void SystemException(AuthenticationException exception,HttpServletResponse response)
    {
        log.info("登录用户名或密码错误异常:"+JSON.toJSONString(exception));
        sendJsonError(SystemCode.USER_LOGIN_ERROR_CODE,SystemCode.USER_LOGIN_ERROR_MSG,response);
    }
    /**
     * @param exception 异常对象
     * @param response
     * 系统级别异常
     */
    @ExceptionHandler(value={Exception.class})
    public void Exception(Exception exception,HttpServletResponse response) {
        log.error("系统异常:", exception);
        sendJsonError(SystemCode.SYS_EXCEPTION_CODE,SystemCode.SYS_EXCEPTION_MSG,response);
    }

    /**
     * 上传的文件超过最大文件限制10M
     * @param e 多文件对象
     */
    @ExceptionHandler(MultipartException.class)
    public void handleMaxUploadSizeExceededException(MultipartException e,HttpServletResponse response) {
        log.error("上传的文件超过最大文件限制:",e);
        sendJsonError(SystemCode.FILE_TOO_LARGE,SystemCode.FILE_TOO_LARGE_DESC,response);
    }

}
