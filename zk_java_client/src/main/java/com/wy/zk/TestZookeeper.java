package com.wy.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestZookeeper {
    public static void main(String[] args) throws Exception {
//        create();
        ZooKeeper zooKeeper = new ZooKeeper("47.103.146.210:2181", 30000,
                System.out::println);
       listAll(zooKeeper,"/");
//        Stat stat = new Stat();
//        zooKeeper.getData("/demos",false,stat);
//        zooKeeper.delete("/demos",stat.getCversion());
        zooKeeper.close();
    }

    public static void create() throws IOException, InterruptedException, KeeperException {
     ZooKeeper zooKeeper = new ZooKeeper("47.103.146.210:2181", 30000,
             System.out::println);
     //创建一个节点
        String res = zooKeeper.create("/testzk","hello zk".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("res = " + res);
        zooKeeper.close();
    }

    public static void listAll(ZooKeeper zooKeeper, String path)throws Exception{
        if(path.isEmpty()) {
            return;
        }
        List<String> list = zooKeeper.getChildren(path,false);
        for (String str:list) {
            String currentNodeName = str;
            currentNodeName = "/".equals(path)? path + str:path + "/" + str;
            System.out.println(currentNodeName);
            listAll(zooKeeper,currentNodeName);
        }
    }


}
