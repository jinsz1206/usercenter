package com.jsz.usercenter.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsz.usercenter.common.BaseResponse;
import com.jsz.usercenter.common.ErrorCode;
import com.jsz.usercenter.exception.BusinessException;
import com.jsz.usercenter.model.domain.User;
import com.jsz.usercenter.model.request.UserLoginRequest;
import com.jsz.usercenter.model.request.UserRegisterRequest;
import com.jsz.usercenter.service.UserService;
import com.jsz.usercenter.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.jsz.usercenter.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StrUtil.hasBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.usrRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StrUtil.hasBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword,request);
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(user);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
           throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        //todo 检验合法
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyuser  = userService.getSafeUser(user);
        return ResultUtils.success(safetyuser);

    }


    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout( HttpServletRequest request) {
        if (request == null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        int result =  userService.userLogout(request);
        return ResultUtils.success(result);
    }


    //管理用户接口

    /**
     *  是否管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null){
            return false;
        }
        if (user.getUserRole().equals("admin")){
            return true;
        }
        return false;
    }


    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username,HttpServletRequest request) {
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null){
            userQueryWrapper.like("username", username);
        }
        List<User> userList = userService.list(userQueryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request) {
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }





}
