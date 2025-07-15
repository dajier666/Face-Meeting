package com.ourwork.meetingsystem.Entity;

import lombok.Data;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MeetingParticipation")
public class MeetingParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParticipationID")
    private Integer participationID;  // 参与记录唯一标识

    @Column(name = "UserID", nullable = false)
    private Integer userID;           // 参与用户ID

    @Column(name = "MeetingID", nullable = false)
    private Integer meetingID;        // 关联会议ID

    @Column(name = "RegistrationTime", nullable = false)
    private LocalDateTime registrationTime;    // 报名时间（默认当前时间）

    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;  // 参与状态（枚举类型）

    // 枚举定义（参与状态）
    public enum ParticipationStatus {
        pending, approved, rejected, attended, absent
    }

   /* // 可选：添加用户和会议的关联（需引入User和Meeting类）
    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "MeetingID", insertable = false, updatable = false)
    private Meeting meeting;*/
}
