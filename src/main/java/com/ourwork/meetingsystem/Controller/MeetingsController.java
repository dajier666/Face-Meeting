package com.ourwork.meetingsystem.Controller;

import com.ourwork.meetingsystem.Entity.*;
import com.ourwork.meetingsystem.Service.LoginService;
import com.ourwork.meetingsystem.Service.MeetingServiceOfUser;
;
import com.ourwork.meetingsystem.Utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/Meetings")
public class MeetingsController {
    private final MeetingServiceOfUser meetingServiceOfUser;
    private final LoginService loginService;
    MeetingsController(MeetingServiceOfUser meetingServiceOfUser,LoginService userServiceOfUser){
        this.meetingServiceOfUser=meetingServiceOfUser;
        this.loginService=userServiceOfUser;
    }

    //预约会议
    @RequestMapping("/Book")
    public Result Book(@RequestBody Meeting meeting, HttpServletRequest request){
        return meetingServiceOfUser.BookMeeting(meeting, Objects.requireNonNull(CookieUtils.getUserMessage(request)));
    }

    //查看自己预约的会议
    @RequestMapping("/SelectMyBookedMeeting")
    public Result SelectMyBookedMeeting(HttpServletRequest request){
        return meetingServiceOfUser.SelectMyBookedMeeting(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
    }

    //查看报名自己预约的会议的人
    @RequestMapping("/SelectMyMeetingMembers")
    public Result SelectMyMeetingMembers(@RequestBody Meeting meeting){
        return meetingServiceOfUser.SelectMyMeetingMembers(meeting.getMeetingID());
    }

    //查看已签到的人
    @RequestMapping("/SelectSignedMembers")
    public Result SelectSignedMembers(@RequestBody Meeting meeting){
        return meetingServiceOfUser.SelectSignedMembers(meeting.getMeetingID());
    }

    //根据会议id和会议密码报名会议
    @RequestMapping("RegisterForTheMeeting")
    public Result RegisterForTheMeeting(@RequestBody Meeting meeting,HttpServletRequest request){
        return meetingServiceOfUser.RegisterForTheMeeting(meeting, Objects.requireNonNull(CookieUtils.getUserMessage(request)));
    }

    //查看自己报名的会议
    @RequestMapping("/MyRegisteredMeeting")
    public Result MyRegisteredMeeting(HttpServletRequest request){
        return meetingServiceOfUser.MyRegisteredMeeting(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
    }

    //取消自己报名
    @RequestMapping("/CancelMyRegister")
    public Result CancelMyRegister(@RequestBody MeetingParticipation meetingParticipation,HttpServletRequest request) {
        meetingParticipation.setUserID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
        return meetingServiceOfUser.CancelMyRegister(meetingParticipation);
    }

    //根据会议id和用户id取消报名
    @RequestMapping("/CancelMyMembersRegister")
    public Result CancelMyMembersRegister(@RequestBody MeetingParticipation meetingParticipation) {
        return meetingServiceOfUser.CancelMyRegister(meetingParticipation);
    }

    //根据id取消报名
    @RequestMapping("/CancelMyRegisterByID")
    Result CancelMyRegisterByID(@RequestBody MeetingParticipation meetingParticipation,HttpServletRequest request) {
        meetingParticipation.setUserID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
        return meetingServiceOfUser.CancelMyRegister(meetingParticipation);
    }

    //人脸签到
    @RequestMapping("/FaceCheckIn")
    Result FaceCheckIn(@RequestBody newSign sign,HttpServletRequest request) {
        SignIn sign1=new SignIn();
        sign1.setMeetingID(sign.getMeetingID());
        sign1.setUserID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
        return meetingServiceOfUser.FaceCheckIn(sign.getPhoto(),sign1);
    }

    //开启会议
    @RequestMapping("/open")
    public Result OpenMMeeting(@RequestBody newSign sign,HttpServletRequest request) {
        SignIn sign1=new SignIn();
        sign1.setMeetingID(sign.getMeetingID());
        sign1.setUserID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
        return meetingServiceOfUser.OpenOrCloseMMeeting(sign.getPhoto(),sign1,true);
    }

    //关闭会议
    @RequestMapping("/close")
    public Result CloseMMeeting(@RequestBody newSign sign) {
        SignIn sign1=new SignIn();
        sign1.setMeetingID(sign.getMeetingID());
        sign1.setUserID(sign.getUserID());
        return meetingServiceOfUser.CloseMMeeting(sign1,false);
    }

    //返回用户名字
    @RequestMapping("/GetUserName")
    public Result GetUserName(HttpServletRequest request){
        return loginService.SelectUserByID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
    }

    // 检查用户在特定会议中的签到状态
    @RequestMapping("/CheckMySignInStatus")
    public Result CheckMySignInStatus(@RequestBody MeetingParticipation meetingParticipation, HttpServletRequest request) {
        meetingParticipation.setUserID(Objects.requireNonNull(CookieUtils.getUserMessage(request)));
        return meetingServiceOfUser.GetMeetingByCreatorID(meetingParticipation);

    }
}
