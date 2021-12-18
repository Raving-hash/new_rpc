package com.wy.client;

import com.wy.rpc.WyRpcFactory;
import com.wy.service.UserService;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client {
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException, KeeperException {
        UserService serviceProxy = WyRpcFactory.getServiceProxy(UserService.class);
        System.out.println("serviceProxy = " + serviceProxy.getUser("wy"));
    }
}
