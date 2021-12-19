package com.wy.user.controller;

import com.wy.pojo.User;
import com.wy.rpc.WyRpcFactory;
import com.wy.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController() throws Exception{
        this.userService = WyRpcFactory.getServiceProxy(UserService.class);
    }

    @RequestMapping("/getUsersByName")
    @ResponseBody
    public List<User> getUsersByName(String name) throws RemoteException {
       try{
           return userService.getUserByName(name);
       }catch (Exception e){
           e.printStackTrace();
           System.out.println(" 查询失败");
           return new ArrayList<>();
       }
    }
}
