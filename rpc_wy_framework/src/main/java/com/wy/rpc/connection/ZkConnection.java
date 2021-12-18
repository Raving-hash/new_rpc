package com.wy.rpc.connection;

import lombok.Data;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

@Data
public class ZkConnection {
    //保存zk服务器地址
    private String zkServer;
    //保存超时时间
    private int sessionTimeout;

    public ZkConnection(){
        super();
        this.zkServer = "47.103.146.210:2181";
        this.sessionTimeout = 30000;
    }
    public ZkConnection(String zkServer, int sessionTimeout){
        this.sessionTimeout = sessionTimeout;
        this.zkServer = zkServer;
    }
    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(zkServer, sessionTimeout, watchedEvent -> {});
    }
}
