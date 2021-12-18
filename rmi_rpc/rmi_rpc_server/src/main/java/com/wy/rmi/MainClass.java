package com.wy.rmi;

import com.wy.api.FirstInterface;
import com.wy.rmi.impl.FirstIRMIImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 创建一个服务对象，并注册到registry上
 * RMI的Registry创建时，会自动创建一个子线程，并升级为守护线程
 */
public class MainClass {
    public static void main(String[] args) {
        try{
            System.out.println("服务器开始启动...请稍候，先生");
            FirstInterface first = new FirstIRMIImpl();
            LocateRegistry.createRegistry(9999);
            Naming.bind("rmi://localhost:9999/first",first);
            FirstInterface f = (FirstInterface) Naming.lookup("rmi://localhost:9999/first");
            System.out.println("服务器启动成功，先生"+f.first("11"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
