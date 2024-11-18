package com.jsz.usercenter.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsz.usercenter.model.domain.User;
import com.jsz.usercenter.service.UserService;
import com.jsz.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jsz.usercenter.contant.UserConstant.USER_LOGIN_STATE;


/**
* @author ex_shengzhou.jin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-11-14 16:23:07
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{



    private static final String SALT = "jsz";

    @Resource
    private UserMapper userMapper;

    @Override
    public long usrRegister(String userAccount, String userPassword, String checkPassword) {
        //1. 校验


        //内容为空
        if(StrUtil.hasBlank(userAccount, userPassword, checkPassword)){
            return -1;
        }
        //用户名长度
//        if(StrUtil.length(userAccount) < 4){
//            return -1;
//        }

        //密码长度
        if(StrUtil.length(userPassword) > 15 || StrUtil.length(userPassword) < 6){
            return -1;
        }


        //账户名字不能包含特殊字符,6-12位数
        if(!Validator.isGeneral(userAccount,6,12)){
            return -1;
        }

        //密码2次输入
        if (!StrUtil.equals(userPassword, checkPassword)) {
            return -1;
        }


        //账户不能重复,对数据库的调用放校验最后
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);

        if(count > 0){
            return -1;
        }

        //密码加密
        byte[] bytes = DigestUtil.md5(SALT + userPassword);
        String encryptPassWord = DigestUtil.md5Hex(bytes);
        //插入
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassWord);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1. 校验


        //内容为空
        if(StrUtil.isAllBlank(userAccount, userPassword)){
            return null;
        }

        //密码长度
        if(StrUtil.length(userPassword) > 15 || StrUtil.length(userPassword) < 6){
            return null;
        }


        //账户名字不能包含特殊字符,6-12位数
        if(!Validator.isGeneral(userAccount,6,12)){
            return null;
        }



        //密码加密
        byte[] bytes = DigestUtil.md5(SALT + userPassword);
        String encryptPassWord = DigestUtil.md5Hex(bytes);


        //查询用户存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassWord);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            log.info("user login failed");
            return null;
        }

        //用户脱敏
        User safetyUser = getSafeUser(user);

        //用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);



        return safetyUser;
    }


    @Override
    public User getSafeUser(User user){
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setUserAvatar(user.getUserAvatar());
        safetyUser.setUserGender(user.getUserGender());
        safetyUser.setUserProfile(user.getUserProfile());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());
        safetyUser.setUserGender(user.getUserGender());
        return safetyUser;
    }

}





