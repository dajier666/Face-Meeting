package com.ourwork.meetingsystem.Entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "Meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MeetingID")
    private Integer meetingID;        // 会议唯一标识，自增主键

    @Column(name = "MeetingName", length = 100, nullable = false)
    private String meetingName;       // 会议名称

    @Column(name = "CreatorID", nullable = false)
    private Integer creatorID;        // 会议创建者ID，关联User表

    @Column(name = "CreateTime", nullable = false)
    private LocalDateTime createTime;          // 会议创建时间（默认当前时间）

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;           // 会议开始时间

    @Column(name = "EndTime", nullable = false)
    private LocalDateTime endTime;             // 会议结束时间

    @Column(name = "IsOpened", nullable = false)
    private Boolean isOpened;         // 会议是否开启状态（默认false）

    @Column(name = "MeetingPassword", length = 50, nullable = false)
    private String meetingPassword;   // 会议访问密码

   /* // 可选：添加创建者用户关联（需引入User类）
    @ManyToOne
    @JoinColumn(name = "CreatorID", insertable = false, updatable = false)
    private User creator;*/
}
