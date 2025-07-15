package com.ourwork.meetingsystem;

import com.ourwork.meetingsystem.Utils.BaiduAiUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeetingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingSystemApplication.class, args);
	}

}
