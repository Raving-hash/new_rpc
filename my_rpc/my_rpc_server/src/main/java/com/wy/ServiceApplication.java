package com.wy;

import com.wy.rpc.WyRpcFactory;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class ServiceApplication {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NotBoundException, ClassNotFoundException {
        Class.forName(WyRpcFactory.class.getName());
    }
}
