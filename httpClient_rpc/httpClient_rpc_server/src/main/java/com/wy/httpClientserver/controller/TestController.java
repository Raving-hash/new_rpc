package com.wy.httpClientserver.controller;

import com.wy.httpClient.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {
    @RequestMapping(value = "/test", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public  String test(){
        return "{\"msg\":\"处理返回\"}";
    }

    @RequestMapping(value = "/params", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String params(String name , String password){
        return "{\"msg\":\"返回成功\"}";
    }

    @RequestMapping(value = "/bodyParams",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    @CrossOrigin
    public String testBodyParams(@RequestBody List<User> list){
        System.out.println("list = " + list);
        return list.toString();
    }


}
