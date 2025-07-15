package com.ourwork.meetingsystem.Entity;

import lombok.Data;

@Data
public class newSign {
    private String photo;
    private Integer meetingID;       // 关联会议ID
    private Integer userID;          // 关联用户ID
}
