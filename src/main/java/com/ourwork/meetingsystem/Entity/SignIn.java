package com.ourwork.meetingsystem.Entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "SignIn")
public class SignIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SignID")
    private Integer signID;          // 签到记录唯一标识

    @Column(name = "MeetingID", nullable = false)
    private Integer meetingID;       // 关联会议ID

    @Column(name = "UserID", nullable = false)
    private Integer userID;          // 关联用户ID

    @Column(name = "SignTime", nullable = false)
    private LocalDateTime signTime;           // 签到时间（默认当前时间）

    @Column(name = "VerificationResult", length = 20, nullable = false)
    private String verificationResult;  // 人脸验证结果（成功/失败）

    /*// 可选：添加会议和用户的关联（需引入Meeting和User类）
    @ManyToOne
    @JoinColumn(name = "MeetingID", insertable = false, updatable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;*/
}