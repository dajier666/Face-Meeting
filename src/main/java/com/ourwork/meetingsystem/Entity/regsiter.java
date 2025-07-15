package com.ourwork.meetingsystem.Entity;

import lombok.Data;


@Data
public class regsiter {
    private String username;        // 用户名，用于登录，全局唯一
    private String password;
    private String face;
}
