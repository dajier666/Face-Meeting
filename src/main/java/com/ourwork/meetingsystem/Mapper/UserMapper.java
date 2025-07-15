package com.ourwork.meetingsystem.Mapper;

import com.ourwork.meetingsystem.Entity.User;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;


@Mapper
public interface UserMapper {
    // 根据ID查询用户
    User selectUserById(Integer userID);

    // 查询前20个用户（用于缓存预热）
    List<User> selectTop20Users();

    // 根据用户名查询用户
    List<User> selectUserByUsername(String username);

    // 根据用户名查询单个用户
    User selectUserByUsername1(String username);

    // 添加用户
    int insertUser(User user);

    // 更新用户信息
    int updateUser(User user);

    // 删除用户
    int deleteUser(Integer userID);
}
