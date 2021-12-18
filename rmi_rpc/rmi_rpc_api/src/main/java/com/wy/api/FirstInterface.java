package com.wy.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程服务接口，RMI规定服务接口必须实现REMOTE接口
 */
public interface FirstInterface extends Remote {
        String first(String name) throws RemoteException;
}
