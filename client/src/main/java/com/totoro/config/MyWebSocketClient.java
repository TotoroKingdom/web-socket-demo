package com.totoro.config;

import cn.hutool.core.util.ByteUtil;
import com.totoro.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.ByteBufferUtils;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author:totoro
 * @createDate:2022/9/7
 * @description:
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("websocket 连接成功");
    }

    @Override
    public void onMessage(String s) {
        log.info("websocket 收到消息：" + s);
    }

    @Override
    public void onMessage(ByteBuffer byteBuffer) {
        log.info("websocket 收到消息：" + ByteUtils.getString(byteBuffer));
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("websocket 退出连接。");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        log.info("websocket 连接错误="+e.getMessage());
    }
}
