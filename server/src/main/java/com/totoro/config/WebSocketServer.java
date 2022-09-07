package com.totoro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:totoro
 * @createDate:2022/9/7
 * @description:
 */
@Slf4j
@Component
@ServerEndpoint("/ws/{userId}")
public class WebSocketServer {

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, List<WebSocketServer>> devWebSocetMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){
        this.session = session;
        this.userId = userId;

        if (webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        } else {
            webSocketMap.put(userId,this);
            addOnlineCount();
        }
        log.info("用户连接："+userId+",当前在线用户为："+getOnlineCount());
        sendMessage("{连接成功}");
    }

    @OnClose
    public void onClose(){
        if (webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出："+userId+",当前在线用户为："+getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        log.info("用户消息："+userId+",报文："+message);
        if (StringUtils.isNoneBlank(message)){
            try {

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @OnError
    public void onError(Session session, Throwable error){
        log.info("用户错误："+this.userId+",原因："+error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendInfo(String message, String userId){
        log.info("发送消息到："+userId+",报文："+message);
        if (StringUtils.isNoneBlank(userId) && webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.info("用户"+userId+"，不在线！");
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount ++ ;
    }

    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount -- ;
    }
}
