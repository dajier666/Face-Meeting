package com.ourwork.meetingsystem.Controller;

import com.ourwork.meetingsystem.Entity.Meeting;
import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Entity.User;
import com.ourwork.meetingsystem.Service.LoginService;
import com.ourwork.meetingsystem.Service.MeetingServiceOfAdmin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
//提供给管理员管理用户数据
public class AdminController {
    private final LoginService loginService;
    private final MeetingServiceOfAdmin meetingServiceOfAdmin;
    AdminController(LoginService loginService, MeetingServiceOfAdmin meetingServiceOfAdmin){
        this.loginService=loginService;
        this.meetingServiceOfAdmin=meetingServiceOfAdmin;
    }
    //删除用户以及相关信息
    @RequestMapping("/delete")
    public Result DeleteUser(@RequestBody User user){
        //调用的方法为异步方法，直接返回响应
        loginService.DeleteUser(user,1);
        return Result.success("操作成功");
    }

    //查询用户id与name
    @RequestMapping("/select")
    public Result SelectUser(){
        return loginService.SelectUser();
    }

    //更新用户信息，不存在对应主键则新建一个用户
    @RequestMapping("/update")
    public Result UpdateUser(@RequestBody User user){
        //调用的方法为异步方法，直接返回响应
        loginService.UpdateUser(user,1);
        return Result.success("操作成功");
    }
    //通过ID查找用户
    @RequestMapping("/selectUserByID")
    public Result selectUserByID(@RequestBody User user){
        return loginService.SelectUserByID(user.getUserID());
    }

    //通过名字查找用户
    @RequestMapping("/selectUserByname")
    public Result selectUserByName(@RequestBody User user){
        return loginService.SelectUserByName(user.getUsername());
    }

    //查询所有会议
    @RequestMapping("/SelectAllMeetings")
    public Result SelectAllMeetings(){
        return meetingServiceOfAdmin.SelectAllMeetings();
    }

    //通过会议id查看会议的参加情况（报名人数、签到人数）
    @RequestMapping("/ParticipationOfMeeting")
    public Result ParticipationOfMeeting(@RequestBody Meeting meeting){
        return meetingServiceOfAdmin.ParticipationOfMeeting(meeting.getMeetingID());
    }

    //通过会议id删除会议
    @RequestMapping("/DeleteMeeting")
    public Result DeleteMeeting(@RequestBody Meeting meeting){
        return meetingServiceOfAdmin.DeleteMeetingOfId(meeting.getMeetingID());
    }

    //通过会议id更新会议
    @RequestMapping("/UpdateMeeting")
    public Result UpdateMeeting(@RequestBody Meeting meeting){
        return meetingServiceOfAdmin.UpdateMeetingOfId(meeting);
    }

    //通过ID查找会议
    @RequestMapping("/SelectMeetingByID")
    public Result SelectMeetingByID(@RequestBody Meeting meeting){
        return meetingServiceOfAdmin.SelectMeetingOfId(meeting.getMeetingID());
    }

}
