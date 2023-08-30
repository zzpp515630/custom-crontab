package me.service.cron.socker;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangpeng
 * @create 2023/3/13 13:54
 */
@Slf4j
@Component
//@Scope("prototype")
@ServerEndpoint(value = "/system/log")
public class LogSocketServer {

    private Session session;

    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.info("crontab connect:sessionId:{}新连接", session.getId());
        sessionMap.put(session.getId(), session);
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    }


    @OnClose
    public void onClose() {
        sessionMap.remove(session.getId());
        log.info("crontab connect sessionId:{}连接关闭", this.session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        sessionMap.remove(session.getId());
        log.info("crontab connect sessionId:{}的连接发送错误", this.session.getId());
        error.printStackTrace();
    }


    public static void sendMessage(Object message) {
        sessionMap.values().forEach(sess -> {
            try {
                sess.getBasicRemote().sendObject(JSON.toJSONString(message));
            } catch (IOException | EncodeException e) {
                log.error(e.getMessage(), e);
            }
        });
    }


}
