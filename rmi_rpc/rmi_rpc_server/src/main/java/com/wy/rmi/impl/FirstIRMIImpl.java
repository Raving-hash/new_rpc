package com.wy.rmi.impl;

import com.wy.api.FirstInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 实现远程服务接口，所有的远程服务实现，必须是Remote直接或间接实现类
 */
public class FirstIRMIImpl extends UnicastRemoteObject implements FirstInterface {
    @Override
    public String first(String name) throws RemoteException {
        System.out.println("客户端请求参数是"+name);
        return "欢迎回来，" + name + "先生";
    }
    public FirstIRMIImpl() throws RemoteException{};

}
