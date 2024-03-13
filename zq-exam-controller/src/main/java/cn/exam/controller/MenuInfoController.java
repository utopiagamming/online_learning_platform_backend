package cn.exam.controller;

import cn.exam.config.BaseController;
import cn.exam.domain.zj.ZjMenuInfo;
import cn.exam.query.ZjMenuQuery;
import cn.exam.service.ZjMenuInfoService;
import cn.exam.so.RoleMenuIdSO;
import cn.exam.util.*;
import cn.exam.vo.RoleMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("menu")
public class MenuInfoController extends BaseController {

    @Autowired
    private ZjMenuInfoService  menuInfoService;

    /**
     * 分页
     * @param response 响应体
     * @param query 菜单名通用查询对象
     */
    @RequestMapping("queryPage.htm")
    public void  queryPage(HttpServletResponse response, ZjMenuQuery query){
        ResultDTO<PageResult<List<ZjMenuInfo>>> resultDTO = new ResultDTO<>();
        PageResult<List<ZjMenuInfo>> listPageResult = menuInfoService.queryMenuInfoPage(query);
        resultDTO.setResult(listPageResult);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccessPage(resultDTO,response);
    }

    /**
     *
     * @param roleId 角色id
     * @param response 响应体
     */
    @RequestMapping("queryMenuIdListByRoleId.htm")
    public void queryMenuIdListByRoleId(String roleId, HttpServletResponse response){
        ResultDTO<List<Integer>> resultDTO = new ResultDTO<>();
        List<Integer> integers = menuInfoService.queryMenuIdListByRoleId(roleId);
        resultDTO.setResult(integers);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJsonSuccess(resultDTO,response);
    }

    /**
     *
     * @param roleId 角色id
     * @param response 响应体
     */
    @RequestMapping("queryMenuListByRoleId.htm")
    public void queryMenuListByRoleId(String roleId, HttpServletResponse response){
        ResultDTO<List<RoleMenuVO>> resultDTO = new ResultDTO<>();
        List<RoleMenuVO> menuVOS = menuInfoService.queryMenuTreeByRoleId(roleId);
        resultDTO.setResult(menuVOS);
        resultDTO.buildReturnCode(SystemCode.RET_CODE_SUCC,SystemCode.RET_MSG_SUCC);
        sendJson(resultDTO,response);
    }

    @RequestMapping("updateRoleMenuInfo.htm")
    public void updateRoleMenuInfo(HttpServletResponse response, RoleMenuIdSO so){
        menuInfoService.updateRoleMenuInfo(so);
        sendJsonSuccess(response);
    }

    @RequestMapping("insertMenuInfo.htm")
    public void insertMenuInfo(ZjMenuInfo info ,HttpServletResponse response){
        String currentDateTime = DateUtil.getCurrentDateTime();
        info.setMenuStatus(1);
//        info.setCreateTime(currentDateTime);
//        info.setUpdateTime(currentDateTime);
        menuInfoService.insertMenuInfo(info);
        sendJsonSuccess(response);
    }
}
