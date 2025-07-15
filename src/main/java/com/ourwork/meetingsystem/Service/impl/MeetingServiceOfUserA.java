package com.ourwork.meetingsystem.Service.impl;

import com.ourwork.meetingsystem.Entity.*;
import com.ourwork.meetingsystem.Mapper.MeetingMapper;
import com.ourwork.meetingsystem.Mapper.MeetingParticipationMapper;
import com.ourwork.meetingsystem.Mapper.SignInMapper;
import com.ourwork.meetingsystem.Service.MeetingServiceOfUser;
import com.ourwork.meetingsystem.Utils.BaiduAiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class MeetingServiceOfUserA implements MeetingServiceOfUser {
    private final MeetingMapper meetingMapper;
    private final MeetingParticipationMapper meetingParticipationMapper;
    private final SignInMapper signInMapper;
    private final ThreadPoolTaskExecutor MyPool;
    MeetingServiceOfUserA(MeetingMapper meetingMapper, MeetingParticipationMapper meetingParticipationMapper,SignInMapper signInMapper, @Qualifier("MyPool") ThreadPoolTaskExecutor MyPool){
        this.meetingMapper=meetingMapper;
        this.meetingParticipationMapper=meetingParticipationMapper;
        this.signInMapper=signInMapper;
        this.MyPool=MyPool;
    }
    @Override
    public Result BookMeeting(Meeting meeting,Integer userID) {
        try{
            meeting.setCreatorID(userID);
            return Result.success(meetingMapper.insertMeeting(meeting));
        }catch (Exception e){
            e.printStackTrace();
            log.info("id为{}的用户预约会议失败,请及时处理",meeting.getCreatorID());
            return Result.error("预约会议失败，请稍后再试");
        }
    }

    @Override
    public Result CloseMMeeting(SignIn signIn, Boolean b) {
        try{
            Meeting meeting=new Meeting();
            meeting.setMeetingID(signIn.getMeetingID());
            meeting.setIsOpened(b);
            return Result.success(meetingMapper.updateMeeting(meeting));
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户关闭自己创建的id为{}会议失败,请及时处理",signIn.getMeetingID());
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result SelectMyBookedMeeting(Integer creatorId) {
        try{
            return Result.success(meetingMapper.selectMeetingsByCreator(creatorId));
        }catch (Exception e){
            e.printStackTrace();
            log.info("id为{}的用户查询自己创建的会议失败,请及时处理",creatorId);
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result SelectMyMeetingMembers(Integer meetingId) {
        try{
            return Result.success(meetingParticipationMapper.selectRegisterededMembers(meetingId));
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户查询id为{}的会议报名成员失败,请及时处理",meetingId);
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result SelectSignedMembers(Integer meetingId) {
        try{
            return Result.success(meetingParticipationMapper.selectSignedMembers(meetingId));
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户查询id为{}的会议签到成员失败,请及时处理",meetingId);
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result RegisterForTheMeeting(Meeting meeting,Integer userID) {
        try{
            Meeting meeting1=meetingMapper.selectMeetingById(meeting.getMeetingID());
            if(LocalDateTime.now().isBefore(meeting1.getStartTime())) {
                if (Objects.equals(meeting1.getMeetingPassword(), meeting.getMeetingPassword())) {
                    MeetingParticipation meetingParticipation = new MeetingParticipation();
                    meetingParticipation.setMeetingID(meeting.getMeetingID());
                    meetingParticipation.setUserID(userID);
                    meetingParticipationMapper.insertParticipation(meetingParticipation);
                    return Result.success();
                } else
                    return Result.error("入会密码错误，请检查id或者密码是否正确");
            }
            else
                return Result.error("不在报名时间内，请关注会议时间");
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户查询id为{}的会议的签到成员失败,请及时处理",meeting.getMeetingID());
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result MyRegisteredMeeting(Integer userId) {
        try{
            return Result.success(meetingParticipationMapper.selectParticipationByUser(userId));
        }catch (Exception e){
            e.printStackTrace();
            log.info("id为{}的用户查询自己报名的会议失败,请及时处理",userId);
            return Result.error("查询失败，请稍后再试");
        }
    }

    @Override
    public Result CancelMyRegister(MeetingParticipation meetingParticipation) {
        try{
            meetingParticipation=meetingParticipationMapper.selectParticipationByUserAndMeeting(meetingParticipation);
            meetingParticipationMapper.deleteParticipation(meetingParticipation.getParticipationID());
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户删除id为{}和{}的报名信息失败,请及时处理",meetingParticipation.getUserID(), meetingParticipation.getMeetingID());
            return Result.error("删除失败，请稍后再试");
        }
    }

    @Override
    public Result CancelMyRegisterByID(Integer ParticipationID) {
        try{
            meetingParticipationMapper.deleteParticipation(ParticipationID);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户删除id为{}的报名信息失败,请及时处理",ParticipationID);
            return Result.error("删除失败，请稍后再试");
        }
    }

    @Override
    public Result OpenOrCloseMMeeting(String photo, SignIn signIn,Boolean b) {
        try{
            Result result = FaceCheckIn(photo, signIn);
            if (result.getCode() == 1) {
                Meeting meeting=meetingMapper.selectMeetingById(signIn.getMeetingID());
                if(meeting.getStartTime().isBefore(LocalDateTime.now())&&meeting.getEndTime().isAfter(LocalDateTime.now())) {
                    MyPool.execute(() -> {

                        meeting.setIsOpened(b);
                        meetingMapper.updateMeeting(meeting);
                    });
                    return Result.success();
                }else
                    return Result.error("不在会议时间内，无法开启");
            } else {
                return Result.error("会议开启失败，请确认是本人操作");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户开启会议时出错");
            return Result.error("系统出错，会议开启失败");
        }
    }

    @Override
    public Result FaceCheckIn(String photo, SignIn sign) {
        try{
            String str=BaiduAiUtils.faceSearch(photo);
            if(str==null)
                throw new Exception("调用百度人脸检测失败");
            Integer curID=BaiduAiUtils.jsonPaser(str);
            if(Objects.equals(curID, sign.getUserID())){
                MyPool.execute(()->{
                    sign.setVerificationResult("1");
                    signInMapper.insertSignIn(sign);
                });
                return Result.success();
            }else
                MyPool.execute(()->{
                    sign.setVerificationResult("0");
                    signInMapper.insertSignIn(sign);
                });
                return Result.error("签到失败，请确认是否是本人签到");

        }
        catch (Exception e){
            e.printStackTrace();
            log.info("调用百度人脸检测进行签到失败");
            return Result.error("系统出错了，请稍后再试");
        }
    }

    @Override
    public Result GetMeetingByCreatorID(MeetingParticipation meetingParticipation) {
        try{
            return Result.success(meetingParticipationMapper.selectParticipationByUserAndMeeting(meetingParticipation));
        }catch (Exception e){
            e.printStackTrace();
            log.info("id为{}用户查询自己在id为{}会议信息失败,请及时处理",meetingParticipation.getUserID(),meetingParticipation.getMeetingID());
            return Result.error("删除失败，请稍后再试");
        }
    }

    @Scheduled(fixedDelay = 1000*60*10)
    public void detectedStatusOfMeetings(){
        LocalDateTime now=LocalDateTime.now();
        meetingMapper.selectAllMeetingsOpened().forEach((meeting)->{
            if(meeting.getEndTime().isBefore(now)) {
                meeting.setIsOpened(false);
                meetingMapper.updateMeeting(meeting);
            }
        });
    }
}
