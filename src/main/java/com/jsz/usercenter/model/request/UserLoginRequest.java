package com.jsz.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 13343253423432L;

    private String userAccount;

    private String userPassword;

}
