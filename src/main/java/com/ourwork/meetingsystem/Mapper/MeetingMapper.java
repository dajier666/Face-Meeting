package com.ourwork.meetingsystem.Mapper;

import com.ourwork.meetingsystem.Entity.Meeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {
    // 根据ID查询会议
    Meeting selectMeetingById(Integer meetingID);

    // 查询所有会议
    List<Meeting>  selectAllMeetings();

    // 查询用户创建的所有会议
    List<Meeting> selectMeetingsByCreator(Integer creatorID);

    // 添加会议
    int insertMeeting(Meeting meeting);

    // 更新会议信息
    int updateMeeting(Meeting meeting);

    // 删除会议
    int deleteMeeting(Integer meetingID);

    //查找开启的会议
    List<Meeting> selectAllMeetingsOpened();

    //查找可报名的会议
    List<Meeting> selectAllMeetingsCanSignUp();
}
