package com.ourwork.meetingsystem.Service.impl;

import com.ourwork.meetingsystem.Entity.Meeting;
import com.ourwork.meetingsystem.Entity.Result;
import com.ourwork.meetingsystem.Mapper.MeetingMapper;
import com.ourwork.meetingsystem.Mapper.MeetingParticipationMapper;
import com.ourwork.meetingsystem.Service.MeetingServiceOfAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class MeetingServiceOfAdminA implements MeetingServiceOfAdmin {
    private final MeetingMapper meetingMapper;
    private final MeetingParticipationMapper meetingParticipationMapper;
    MeetingServiceOfAdminA(MeetingMapper meetingMapper,MeetingParticipationMapper meetingParticipationMapper){
        this.meetingMapper=meetingMapper;
        this.meetingParticipationMapper=meetingParticipationMapper;
    }
    @Override
    public Result SelectAllMeetings() {
        try{
            return Result.success(meetingMapper.selectAllMeetings());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("查询所有会议时出现错误");
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result ParticipationOfMeeting(Integer id) {
        try{
            Map<String, Integer> Participation=new HashMap<>();
            Participation.put("Participation", meetingParticipationMapper.countMeetingParticipants1(id));
            Participation.put("SignIn", meetingParticipationMapper.countMeetingParticipants2(id));
            return Result.success(Participation);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("查询id为{}会议参加情况时出现错误",id);
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result DeleteMeetingOfId(Integer id) {
        try{
            meetingMapper.deleteMeeting(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("删除id为{}的用户时出现错误", id);
            return Result.error("删除失败，请稍后再试");
        }
    }

    @Override
    public Result UpdateMeetingOfId(Meeting meeting) {
        try{
            meetingMapper.updateMeeting(meeting);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新id为{}的用户时出现错误", meeting.getMeetingID());
            return Result.error("更新失败，请稍后再试");
        }
    }

    @Override
    public Result SelectMeetingOfId(Integer id) {
        try{
            return Result.success(meetingMapper.selectMeetingById(id));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("查询id为{}会议时出现错误",id);
            return Result.error("查询失败，请稍后再试");
        }
    }
}
