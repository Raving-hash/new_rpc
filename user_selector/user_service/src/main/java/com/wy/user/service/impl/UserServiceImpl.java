package com.wy.user.service.impl;

import com.wy.pojo.User;
import com.wy.rpc.WyRpcFactory;
import com.wy.user.mapper.UserMapper;
import com.wy.user.service.UserService;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Service
public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws IOException, InterruptedException, KeeperException,RemoteException {
        WyRpcFactory.registerService(UserService.class,this);
    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserByName(String name) throws RemoteException {
        return userMapper.selectByName(name);
    }
}
