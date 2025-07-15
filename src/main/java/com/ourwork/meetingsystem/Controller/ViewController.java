package com.ourwork.meetingsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ViewController {


    @RequestMapping("/login")
    public String redirectToDefaultPage1() {
        // 重定向到指定页面
        return "redirect:/html/login.html";
    }

    @RequestMapping("/admin")
    public String redirectToDefaultPage2() {
        return "redirect:/html/hd.html";
    }

    @RequestMapping("/user")
    public String redirectToDefaultPage3() {
        return "redirect:/html/u.html";
    }
}
