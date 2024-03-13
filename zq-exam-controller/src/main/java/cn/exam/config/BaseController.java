package cn.exam.config;

import cn.exam.util.PageResult;
import cn.exam.util.ResultDTO;
import cn.exam.util.SystemCode;
import cn.exam.vo.UserVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类BaseController.java的实现描述：controller公用代码
 */

public class BaseController {

	static final Log log = LogFactory.getLog(BaseController.class);
	@Autowired
	private UserUtil userUtil;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	protected void sendJson(Object obj, HttpServletResponse response) {
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

	/**
	 * 发送一个完成的JSON响应<br>
	 *
	 * @param response 响应体
	 */
	protected void sendJsonSuccess(HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("description", SystemCode.RET_MSG_SUCC);
		map.put("code", SystemCode.RET_CODE_SUCC);
		sendJson(map, response);
	}

	/**
	 * 发送一个完成的JSON响应<br>
	 *
	 * @param response 响应体
	 */
	protected void sendJsonSuccess(String description, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("description", description);
		map.put("code", SystemCode.RET_CODE_SUCC);
		sendJson(map, response);
	}

	/**
	 * 发送一个完成的JSON响应,同时带有结果对象
	 *
	 * @param result 返回结果泛型集合
	 * @param response 响应体
	 */
	protected <T> void sendJsonSuccess(ResultDTO<T> result, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", SystemCode.RET_CODE_SUCC);
		map.put("description", result.getDescription());
		map.put("result", result.getResult());
		sendJson(map, response);
	}

	/**
	 * 发送一个完成的JSON响应,同时带有结果和分页对象
	 *
	 * @param result 返回结果泛型集合
	 * @param response 响应体
	 */
	protected <T> void sendJsonSuccessPage(ResultDTO<PageResult<T>> result, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", SystemCode.RET_CODE_SUCC);
		map.put("description", SystemCode.RET_MSG_SUCC);
		map.put("result", result.getResult());
		sendJson(map, response);
	}

	protected void sendJsonResultSuccess(String code, String description, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		map.put("description", description);
		sendJson(map, response);
	}

	protected void sendJsonError(String code, String description, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("description", description);
		map.put("code", code);
		sendJson(map, response);
	}


	/**
	 * 通用封装
	 *
	 * @param result 返回结果泛型集合
	 * @param response 响应体
	 */
	protected <T> void sendJsonResult(ResultDTO<T> result, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("description", result.getDescription());
		map.put("code", result.getCode());
		map.put("result", result.getResult());
		sendJson(map, response);
	}

    protected UserVO getCurrentUserInfo(){
		return userUtil.getUser();
	}

}
