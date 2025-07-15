package com.ourwork.meetingsystem.Mapper;

import com.ourwork.meetingsystem.Entity.MeetingParticipation;
import com.ourwork.meetingsystem.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MeetingParticipationMapper {
    // 查询用户在某个会议的参与记录
    MeetingParticipation selectParticipationByUserAndMeeting(MeetingParticipation participation);

    //查询某个用户报名的所有会议
    List<MeetingParticipation> selectParticipationByUser(Integer userID);

    // 查询会议的所有参与记录
    List<MeetingParticipation> selectParticipationsByMeeting(Integer meetingID);

    // 添加参与记录
    void insertParticipation(MeetingParticipation participation);

    // 更新参与状态
    void updateParticipationStatus(MeetingParticipation participation);

    // 删除参与记录
    void deleteParticipation(Integer ParticipationID);

    // 查询会议的报名人数
    int countMeetingParticipants1(Integer meetingID);

    // 查询会议的签到人数
    int countMeetingParticipants2(Integer meetingID);

    //查询某个会议已签到的用户id和用户名
    List<User> selectSignedMembers(Integer meetingID);

    //查询报名某个会议的用户id和用户名
    List<User> selectRegisterededMembers(Integer meetingID);
}
