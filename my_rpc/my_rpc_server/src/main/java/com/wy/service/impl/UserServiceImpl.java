package com.wy.service.impl;

import com.wy.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    @Override
    public String getUser(String name) throws RemoteException {
        System.out.println("name = " + name);
        return "{\"name\":\"+name+\",\"age\":\"20\"}";
    }

    public UserServiceImpl()throws RemoteException{
        super();
    }
}
