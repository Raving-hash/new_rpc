package com.wy.rmi;

import com.wy.api.FirstInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 返回Proxy对象，一定实现了服务接口
 */
public class ClientMainClass {
    public static void main(String[] args) {
        FirstInterface first = null;
        try {
            first = (FirstInterface) Naming.lookup("rmi://localhost:9999/first");
            String raving = first.first("Raving");
            System.out.println(raving);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
