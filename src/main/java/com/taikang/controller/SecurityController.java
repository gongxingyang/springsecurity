package com.taikang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itw_gongxy
 * @date 2020/2/24 17:05
 */
@Controller
public class SecurityController {


    @RequestMapping("/access/user")
    public @ResponseBody
    String isUser(){
        return "this is user";
    }

    @RequestMapping("/access/read")
    public  @ResponseBody String isRead(){
        return "this is read";
    }

    @RequestMapping("/access/admin")
    public @ResponseBody String isAdmin(){
        return "this is admin";
    }

    @RequestMapping("/index")
    public String goIndex(){
        return "forward:/index.html";
    }

}
