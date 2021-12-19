package com.wy.user.service;

import com.wy.pojo.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserService extends Remote {

    List<User> getUserByName(String name)throws RemoteException;

}
