package com.ourwork.meetingsystem.Service;

import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Entity.User;

public interface LoginService {
    //登录
    Result login(User user);

    //注册并采集人脸
    Result register(User user,String face);

    //通过id删除用户
    void DeleteUser(User user,Integer i);

    //更新用户
    void UpdateUser(User user,Integer i);

    //查找
    Result SelectUser();

    //通过id查找用户
    Result SelectUserByID(Integer id);

    //通过name查找用户
    Result SelectUserByName(String username);

}
