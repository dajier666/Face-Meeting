package com.ourwork.meetingsystem.Service.impl;

import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Entity.User;
import com.ourwork.meetingsystem.Mapper.UserMapper;
import com.ourwork.meetingsystem.Service.LoginService;
import com.ourwork.meetingsystem.Utils.BaiduAiUtils;
import com.ourwork.meetingsystem.Utils.PasswordUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceA implements LoginService {
    private final UserMapper userMapper;
    private final Cache<String,User> cache;
    private final ThreadPoolTaskExecutor MyPool;

    public LoginServiceA(UserMapper userMapper, @Qualifier("MyPool") ThreadPoolTaskExecutor MyPool) {
        this.userMapper = userMapper;
        this.MyPool = MyPool;
        //初始化本地缓存
        cache = CacheBuilder.newBuilder()
                .maximumSize(100) // 最大缓存条目数
                .expireAfterWrite(15, TimeUnit.MINUTES) // 写入后 15 分钟过期
                .build();
        //缓存预热
        userMapper.selectTop20Users().forEach(user1 -> {
            cache.put(user1.getUsername(),user1);
        });
    }

    @Override
    public Result login(User user) {
        Result result = Result.error("未查找到对应用户信息，您可能未注册");
        try {
            result=MyPool.submit(()->{
                //先查找本地缓存，再查找数据库
                User user1=cache.getIfPresent(user.getUsername());
                if (user1==null){
                    user1=userMapper.selectUserByUsername1(user.getUsername());
                }
                //更新缓存
                User finalUser = user1;
                MyPool.execute(()->cache.put(finalUser.getUsername(), finalUser));
                //返回登录结果
                if(user1.getPassword().equals(user.getPassword())) {
                    user.setUserID(user1.getUserID());
                    return Result.success(user1.getLevel());
                }
                else {
                    return Result.error("用户名或密码错误");
                }
            }).get();
        } catch (Exception e) {
            log.info("发生异常，错误类型: {}", e.getClass().getSimpleName(), e);
        }
        return result;
    }

    @Transactional
    @Override
    public Result register(User user,String face) {
        //用户名重复数据库会报错
        Result result = Result.error("用户名可能重复");
        try {
            result=MyPool.submit(()->{
                PasswordUtils passwordUtils = new PasswordUtils();
                //检查密码和用户名规范
                if(passwordUtils.PasswordCheck(user.getPassword())&&user.getUsername()!=null){
                    //更新数据库和缓存
                    userMapper.insertUser(user);
                    User user1=userMapper.selectUserByUsername1(user.getUsername());
                    try {
                        String s=String.valueOf(BaiduAiUtils.faceMessageCheck(BaiduAiUtils.add(user1.getUserID(),face)).get("error_code"));
                        if(Objects.equals(s, "0")){
                            return Result.success();
                        }else {
                            throw new Exception("人脸检测失败");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("用户注册时人脸检测失败");
                        userMapper.deleteUser(user1.getUserID());
                        return Result.error("人脸检测失败，请稍后再试");
                    }
                }
                else
                    return Result.error("密码规则：包含至少一个大写字母、一个小写字母、一个数字和一个特殊字符，长度为 8 到 20 个字符");
            }).get();
        } catch (Exception e) {
            log.info("发生异常，错误类型: {}", e.getClass().getSimpleName(), e);
            log.info("用户注册时插入失败");
        }
        return result;
    }

    @Async("TaskExecutor1")
    @Transactional
    @Override
    public void DeleteUser(User user,Integer TryTimes) {
        if(TryTimes>10){
            log.info("DeleteUser重试多次均失败，放弃重试");
            return;
        }
        try {
                //删除数据库和缓存中的用户信息
                userMapper.deleteUser(user.getUserID());
                cache.invalidate(user.getUsername());
                //调用其他服务删除该用户的对应缓存和数据库信息


        } catch (Exception e) {
            log.info("发生异常，错误类型: {}", e.getClass().getSimpleName(), e);
            log.info("DeleteUser执行失败{}次，尝试重新执行",TryTimes);
            DeleteUser(user,TryTimes+1);
        }
    }

    @Override
    public Result SelectUserByID(Integer id) {
        return Result.success(userMapper.selectUserById(id));
    }

    @Override
    public Result SelectUserByName(String username) {
        return Result.success(userMapper.selectUserByUsername(username));
    }

    //不存在对应用户id则创建一个新用户
    @Async("TaskExecutor1")
    @Override
    public void UpdateUser(User user,Integer TryTimes) {
        if(TryTimes>10){
            log.info("UpdateUser重试多次均失败，放弃重试");
            return;
        }
        try {
            userMapper.updateUser(user);
            cache.invalidate(user.getUsername());
        } catch (Exception e) {
            log.info("发生异常，错误类型: {}", e.getClass().getSimpleName(), e);
            log.info("UpdateUser执行失败{}次，尝试重新执行",TryTimes);
            UpdateUser(user,TryTimes+1);
        }
    }

    @Override
    public Result SelectUser() {
        try {
            List<User> userList=userMapper.selectTop20Users();
            return Result.success(userList);
        } catch (Exception e) {
            log.info("发生异常，错误类型: {}", e.getClass().getSimpleName(), e);
            log.info("管理员查询用户数据失败");
            return Result.error("查询数据库失败，请重试");
        }
    }
}
