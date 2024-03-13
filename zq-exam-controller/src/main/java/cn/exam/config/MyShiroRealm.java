package cn.exam.config;


import cn.exam.service.UserInfoService;
import cn.exam.util.LoginErrorException;
import cn.exam.util.SystemCode;
import cn.exam.vo.MenuInfoVO;
import cn.exam.vo.UserVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class MyShiroRealm extends AuthorizingRealm {
	protected static final Log log = LogFactory.getLog(MyShiroRealm.class);
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 校验权限时调用
	 * @param principals 密钥
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<MenuInfoVO> permission = userUtil.getPermission();
		if (null == permission){
			return authorizationInfo;
		}
		for (MenuInfoVO menuInfo:permission){
			authorizationInfo.addStringPermission(menuInfo.getMenuIndex());
		}
		return authorizationInfo;
	}

	/**
	 * 验证身份信息时调用
	 * @param token 身份验证信息
	 * @throws AuthenticationException 异常对象
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		//获取用户的输入的账号.
		String userId = (String)token.getPrincipal();
		UserVO shiroUserInfoVO = userInfoService.queryUserInfoByName(userId);
		//shiro验证账号
		if (shiroUserInfoVO==null){
			throw new LoginErrorException(SystemCode.USER_LOGIN_ERROR_CODE,SystemCode.USER_LOGIN_ERROR_MSG);
		}
		return new SimpleAuthenticationInfo(
				shiroUserInfoVO,
				shiroUserInfoVO
				.getPassword(),
				getName()
		);
	}
}
