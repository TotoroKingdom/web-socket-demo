package com.totoro.config;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @author:totoro
 * @createDate:2022/9/7
 * @description:
 */
@Component
@Slf4j
public class WebSocketConfig {

    @Autowired
    private Environment env;

    @Bean
    public WebSocketClient webSocketClient(){
        String ws = "ws://localhost:9090/ws/" + UUID.randomUUID().toString();

        try {
            log.info("ws="+ws);
            WebSocketClient webSocketClient = new MyWebSocketClient(new URI(ws));
            webSocketClient.connect();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("进入定时器");
                    if (webSocketClient.isClosed()){
                        log.info("断线重连");
                        webSocketClient.connect();
                    }
                }
            },1000,5000);
            return webSocketClient;
        } catch (URISyntaxException e){
            e.printStackTrace();
        }

        return null;
    }


}
