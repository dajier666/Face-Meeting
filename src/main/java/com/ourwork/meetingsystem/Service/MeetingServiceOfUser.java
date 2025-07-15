package com.ourwork.meetingsystem.Service;

import com.ourwork.meetingsystem.Entity.Meeting;
import com.ourwork.meetingsystem.Entity.MeetingParticipation;
import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Entity.SignIn;

public interface MeetingServiceOfUser {
    //预约会议
    Result BookMeeting(Meeting meeting,Integer userID);

    //查看自己预约的会议
    Result SelectMyBookedMeeting(Integer creatorId);

    //查看报名自己预约的会议的人
    Result SelectMyMeetingMembers(Integer meetingId);

    //查看已签到的人
    Result SelectSignedMembers(Integer meetingId);

    //根据会议id和会议密码报名会议
    Result RegisterForTheMeeting(Meeting meeting,Integer userID);

    //查看自己报名的会议
    Result MyRegisteredMeeting(Integer userId);

    //根据会议id和用户id取消报名
    Result CancelMyRegister(MeetingParticipation meetingParticipation);

    //根据id取消报名
    Result CancelMyRegisterByID(Integer ParticipationID);

    //人脸签到
    Result FaceCheckIn(String photo, SignIn signIn);

    //开启会议
    Result OpenOrCloseMMeeting(String photo,SignIn signIn,Boolean b);

    //关闭会议
    Result CloseMMeeting(SignIn signIn,Boolean b);

    //获取可报名会议
    Result GetMeetingByCreatorID(MeetingParticipation meetingParticipation);

    //查看自己是否签到

}
