package com.wy.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义服务接口
 */
public interface UserService extends Remote {
    //根据用户名查询用户，返回JSON描述用户对象。
    String getUser(String name) throws RemoteException;
}
