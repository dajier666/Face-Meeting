package com.ourwork.meetingsystem.Mapper;

import com.ourwork.meetingsystem.Entity.SignIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SignInMapper {
    // 查询用户在某个会议的签到记录
    SignIn selectSignInByUserAndMeeting(
            @Param("userID") Integer userID,
            @Param("meetingID") Integer meetingID);

    // 查询会议的所有签到记录
    List<SignIn> selectSignInsByMeeting(Integer meetingID);

    // 添加签到记录
    int insertSignIn(SignIn signIn);

    // 更新签到记录
    int updateSignIn(SignIn signIn);

    // 删除签到记录
    int deleteSignIn(Integer signID);
}
