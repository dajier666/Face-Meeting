package com.ourwork.meetingsystem.Entity;


import lombok.Data;
import jakarta.persistence.*;

@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userID;         // 用户唯一标识，自增主键

    @Column(name = "Username", length = 50, unique = true, nullable = false)
    private String username;        // 用户名，用于登录，全局唯一

    @Column(name = "Password", length = 100, nullable = false)
    private String password;        // 密码

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private UserLevel level;        // 用户身份（枚举类型）

    // 枚举定义（用户身份）
    public enum UserLevel {
        user,
        administrator
    }
}