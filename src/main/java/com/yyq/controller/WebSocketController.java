package com.yyq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.yyq.entity.WebSocket;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
@ServerEndpoint("/chat/{phone}/{nick}/{sex}/{head}")
public class WebSocketController {

    //记录连接用户
    public static ConcurrentHashMap<String, Session> online = new ConcurrentHashMap<>();

    WebSocket webSocket = new WebSocket();
    private String phone;

    @OnOpen
    public void open(@PathParam("phone") String phone,
                     @PathParam("nick") String nick,
                     @PathParam("sex") String sex,
                     @PathParam("head") String head,
                     Session session){

        if (!phone.equalsIgnoreCase("undefined")){
            //添加到记录中
            online.put(phone,session);
        }


        this.phone = phone;

        webSocket.setCode("100");
        webSocket.setMsg("来了");
        webSocket.setNick(nick);
        webSocket.setSex(sex);
        webSocket.setHead(head);
        webSocket.setPhone(phone);
        webSocket.setPerson(String.valueOf(online.size()));

        String jsonString = JSON.toJSONString(webSocket);
        send(jsonString);

        System.out.println(jsonString);
    }

    @OnMessage
    public void receive(Session session, String msg){
        //接收消息，转发消息
        //群发
        webSocket.setCode("101");
        webSocket.setMsg(msg);

        String jsonString = JSON.toJSONString(webSocket);
        send(jsonString);

        System.out.println(jsonString);
    }

    @OnError
    public void error(Session session, Throwable throwable){
        System.out.println("错误"+throwable.getMessage());
        System.out.println(online);
        online.clear();
    }

    @OnClose
    public void close(Session session){


        webSocket.setCode("100");
        webSocket.setMsg("溜了");
        webSocket.setPerson(String.valueOf(online.size()));

        String jsonString = JSON.toJSONString(webSocket);
        send(jsonString);

        System.out.println(jsonString);

        online.remove(phone);
    }

    //发消息
    private void send(String jsonData){
        for (String user:online.keySet()){

            try {
                online.get(user).getBasicRemote().sendText(jsonData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
