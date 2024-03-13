package cn.exam.service.impl;

import cn.exam.domain.zj.ZjUserInfo;
import cn.exam.query.UserQuery;
import cn.exam.service.UserInfoService;
import cn.exam.util.PageResult;
import cn.exam.util.PageUtil;
import cn.exam.vo.UserPageVO;
import cn.exam.vo.UserRoleVO;
import cn.exam.dao.mapper.zj.ZjUserInfoMapper;

import cn.exam.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private ZjUserInfoMapper userInfoMapper;


    @Override
    public UserVO queryUserInfoByName(String userId) {
        UserVO user = userInfoMapper.queryShiroUserInfoByUserName(userId);
        List<UserRoleVO> roleBean = userInfoMapper.queryUserRoleByUserId(userId);
        if (!ObjectUtils.isEmpty(roleBean)){
            user.setRole(roleBean);
        }
        return user;
    }

    @Override
    public PageResult<List<UserPageVO>> queryPage(UserQuery query) {
        return PageUtil.execute(() -> userInfoMapper.queryPage(query), query);
    }
}
