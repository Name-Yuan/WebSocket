package com.source.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/webSocket")
@Component
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> writeArraySet = new CopyOnWriteArraySet<>();


    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        writeArraySet.add(this);
        log.info("创建连接");
    }

    @OnClose
    public void onClose(){
        writeArraySet.remove(this);
        log.info("断开连接");
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket消息】收到客户端发来的消息:{}",message);
    }

    public void sendMessage(String message){
        for (WebSocket webSocket : writeArraySet){
            log.info("【websocket消息】广播消息，message = {}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
