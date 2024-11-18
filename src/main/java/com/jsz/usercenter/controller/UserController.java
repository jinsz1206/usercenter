package com.jsz.usercenter.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsz.usercenter.model.domain.User;
import com.jsz.usercenter.model.request.UserLoginRequest;
import com.jsz.usercenter.model.request.UserRegisterRequest;
import com.jsz.usercenter.service.UserService;
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
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StrUtil.hasBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        long id = userService.usrRegister(userAccount, userPassword, checkPassword);
        return id;
    }

    @PostMapping("/logion")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StrUtil.hasBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);
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
    public List<User> searchUser(String username,HttpServletRequest request) {
        if (!isAdmin(request)){
            return null;
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null){
            userQueryWrapper.like("username", username);
        }
        List<User> userList = userService.list(userQueryWrapper);
        return userList.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id,HttpServletRequest request) {
        if (!isAdmin(request)){
            return false;
        }

        if (id <= 0){
            return false;
        }
        return userService.removeById(id);
    }



}
