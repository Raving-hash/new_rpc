package com.wy.rpc.registry;

import com.wy.rpc.connection.ZkConnection;
import lombok.Data;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;

/**
 * 注册器工具
 * 通过zk连接对象，和传入的Remote接口实现对象，完成RMI地址的拼接
 * 缺少LocateRegistry对象，缺少当前类中属性的赋值过程。整体流程，缺少ZkConnection的创建
 */
@Data
public class WyRpcRegistry {
    //zk连接对象
    private ZkConnection connection;
    private String ip;
    private int port;

    /**
     * 注册服务的方法
     * @param serviceInterface 服务接口类对象
     * @param serviceObject 服务实例对象
     */
    public <T extends Remote> void registerService(Class<T> serviceInterface, T serviceObject) throws IOException, InterruptedException, KeeperException {
        String rmi = "rmi://"+ip+":"+port+"/"+serviceInterface.getName();
        String path = "/wy/rpc/"+serviceInterface.getName();
       List<String> nodes = connection.getConnection().getChildren("/wy/rpc",false);
       if(nodes.contains(serviceInterface.getName())) {
           Stat stat = new Stat();
           connection.getConnection().getData(path,false,stat);
           connection.getConnection().delete(path,stat.getCversion());
       }
        connection.getConnection().create(path,rmi.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        LocateRegistry.getRegistry(port);
        //把服务对象在RMI的Registry上注册
        Naming.rebind(rmi,serviceObject);
    }

    /**
     * 根据服务接口类型，访问zk，获取RMI的远程代理对象
     * @return
     */
    public <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, NotBoundException, InterruptedException, KeeperException {
        String path = "/wy/rpc/"+serviceInterface.getName();
        byte[] data = connection.getConnection().getData(path, false, null);
        String rmi = new String(data);
        LocateRegistry.getRegistry(port);
        return (T)Naming.lookup(rmi);
    }

}
