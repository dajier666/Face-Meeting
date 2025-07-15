package com.ourwork.meetingsystem.Service;

import com.ourwork.meetingsystem.Entity.Meeting;
import com.ourwork.meetingsystem.Entity.Result;

import java.util.List;
import java.util.Map;

public interface MeetingServiceOfAdmin {
    //查询所有会议
    Result SelectAllMeetings();

    //通过会议id查看会议的参加情况（报名人数、签到人数）
    Result ParticipationOfMeeting(Integer id);

    //根据id删除会议
    Result DeleteMeetingOfId(Integer id);

    //更新会议
    Result UpdateMeetingOfId(Meeting meeting);

    //根据id查询会议
    Result SelectMeetingOfId(Integer id);
}
