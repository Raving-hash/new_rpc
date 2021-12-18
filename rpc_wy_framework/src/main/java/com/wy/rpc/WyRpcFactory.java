package com.wy.rpc;

import com.wy.rpc.connection.ZkConnection;
import com.wy.rpc.registry.WyRpcRegistry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

/**
 * 框架入口
 */
public class WyRpcFactory {
    //用于保存配置信息
    private static final Properties CONFIG = new Properties();
    //用于保存zk连接
    private static  ZkConnection CONNECTION ;
    //用于保存注册器
    private static  WyRpcRegistry REGISTRY;
    //保存注册服务信息
    private static final Properties SERVICES = new Properties();

    static {
        InputStream inputStream = WyRpcRegistry.class.getClassLoader().getResourceAsStream("wy-rpc.properties");
        try {
            CONFIG.load(inputStream);
            //获取服务端IP
            String serverIp = CONFIG.getProperty("registry.ip") == null?"localhost":CONFIG.getProperty("registry.ip");
            //获取服务端端口号
            int serverPort = CONFIG.getProperty("registry.port") == null? 9090 : Integer.parseInt(CONFIG.getProperty("registry.port"));
            String zkServer = CONFIG.getProperty("zk.server") == null? "localhost:2181":CONFIG.getProperty("zk.server");
            int zkSessionTimeout = CONFIG.getProperty("zk.sessionTimeout") == null? 30000 : Integer.parseInt(CONFIG.getProperty("zk.sessionTimeout"));
            //创建一个zk连接对象
            CONNECTION = new ZkConnection(zkServer,zkSessionTimeout);
            //创建一个注册器
            REGISTRY = new WyRpcRegistry();
            REGISTRY.setIp(serverIp);
            REGISTRY.setPort(serverPort);
            REGISTRY.setConnection(CONNECTION);
            //创建一个RMI的Registry
            LocateRegistry.createRegistry(serverPort);

            //初始化zk的父节点
            List<String> list = CONNECTION.getConnection().getChildren("/",false);
            if(!list.contains("wy")){
                CONNECTION.getConnection().create("/wy",null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            list = CONNECTION.getConnection().getChildren("/wy",false);
            if(!list.contains("rpc")){
                CONNECTION.getConnection().create("/wy/rpc",null,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
            //初始化服务，并注册
            InputStream servicesStream = WyRpcFactory.class.getClassLoader().getResourceAsStream("wy-rpc-services.properties");
            SERVICES.load(servicesStream);
            if (!SERVICES.isEmpty()){
                for (Object key:SERVICES.keySet()){
                    String value = SERVICES.getProperty(key.toString());
                    //获取接口对象和服务对象
                    Class<Remote> serviceInterface =(Class<Remote>)Class.forName(key.toString());
                    Remote serviceObject =(Remote) Class.forName(value).newInstance();
                    //注册服务
                    REGISTRY.registerService(serviceInterface,serviceObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (InterruptedException | KeeperException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //提供一个快速的注册服务方法
    public static <T extends Remote> void registerService(Class<T> serviceInterface, T serviceObject) throws IOException, InterruptedException, KeeperException {
         REGISTRY.registerService(serviceInterface,serviceObject);
    }
    //提供一个获取服务端代理对象的方法
    public static <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, NotBoundException, InterruptedException, KeeperException {
        return REGISTRY.getServiceProxy(serviceInterface);
    }
}
