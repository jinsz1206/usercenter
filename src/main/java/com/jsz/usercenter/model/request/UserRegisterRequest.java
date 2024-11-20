package com.jsz.usercenter.model.request;


import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 15476859765876L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;


}