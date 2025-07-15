package com.ourwork.meetingsystem.Controller;

import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Entity.User;
import com.ourwork.meetingsystem.Entity.regsiter;
import com.ourwork.meetingsystem.Mapper.UserMapper;
import com.ourwork.meetingsystem.Service.LoginService;
import com.ourwork.meetingsystem.Utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    private final UserMapper userMapper;

    public LoginController(LoginService loginService, UserMapper userMapper) {
        this.loginService = loginService;
        this.userMapper = userMapper;
    }

    //登录
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public Result login(@RequestBody User user){
        Result result = loginService.login(user);
        //登录成功发放jwt令牌
        if (result.getCode()==1){
            User user1=userMapper.selectUserByUsername1(user.getUsername());
            Map<String,Object> usermap=new HashMap<>();
            usermap.put("name",user1.getUsername());
            usermap.put("id",user1.getUserID());
            String userJwt= JwtUtils.generateJwt(usermap);
            user1.setUsername(userJwt);
            user1.setPassword(null);
            if(user1.getLevel()== User.UserLevel.user)
                user1.setUserID(0);
            else
                user1.setUserID(1);
            return Result.success(user1);
        }
        return result;
    }

    //注册
    @RequestMapping("/register")
    @CrossOrigin(origins = "*")
    public Result register(@RequestBody regsiter res){
        User user=new User();
        user.setUsername(res.getUsername());
        user.setPassword(res.getPassword());
        System.out.println(res.getFace());
        return loginService.register(user,res.getFace());
    }




}
