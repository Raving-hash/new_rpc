package com.wy.httpClient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用main方法，测试httpClient技术
 */
public class TestHttpClient {
    public static void main(String[] args) throws IOException, URISyntaxException {
//        testGetNoParams();
//        testGetParams();
//         testPostParams();
        testPostParams2();
    }
    //无参数get
    public static void testGetNoParams() throws IOException {
        //创建客户端对象
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:80/test");
        HttpResponse res =  client.execute(get);
        HttpEntity entity = res.getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        System.out.println("s = " + s);
    }

    public static void testGetParams() throws IOException, URISyntaxException {
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://localhost:80/params");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("name","zhangsan"));
        nvps.add(new BasicNameValuePair("password", "fish or palm"));
        builder.addParameters(nvps);
        URI build = builder.build();
        String s = EntityUtils.toString(client.execute(new HttpGet(build)).getEntity());
        System.out.println("s = " + s);
    }

    /**
     * 测试有参数post
     */
    public static void testPostParams() throws URISyntaxException, IOException {
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://localhost/params");
        builder.addParameter("name1", "password1");
        builder.addParameter("name2", "password2");
        URI uri = builder.build();
        HttpPost  post = new HttpPost(uri);
        String s = EntityUtils.toString(client.execute(post).getEntity(), "utf-8");
        System.out.println("s = " + s);
    }

    public  static void testPostParams2() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost bodyPost = new HttpPost("http://localhost/bodyParams");
        User u1 = new User("name1", "password1");
        User u2 = new User("name2", "password2");
        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        ObjectMapper mapper = new ObjectMapper();
        String paramString = mapper.writeValueAsString(list);
        HttpEntity entity = new StringEntity(paramString,"application/json","utf-8");
        bodyPost.setEntity(entity);
        String s = EntityUtils.toString(client.execute(bodyPost).getEntity());
        JavaType type = mapper.getTypeFactory().constructType(List.class, User.class);
        List<User> userList = mapper.readValue(s, type);
        System.out.println(userList);
    }
}
