package com.line.backstage.websocket;

import com.line.backstage.redis.SubscribeListener;
import com.line.backstage.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，但在springboot中连容器都是spring管理的。
 * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/current/s/{coinCode}/{sid}")
public class KLineDataServer {

    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的webSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    public static CopyOnWriteArraySet<KLineDataServer> webSocketSet = new CopyOnWriteArraySet<KLineDataServer>();

    /**
     * 货品列表，不重复的
     */
    public static CopyOnWriteArrayList<String> codeList = new CopyOnWriteArrayList<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    public SubscribeListener subscribeListener;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("coinCode") String coinCode, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        subscribeListener = new SubscribeListener();
        subscribeListener.setSession(session, sid, coinCode);
        // 新增到货品列表
        codeList.addIfAbsent(coinCode);
        System.out.println("当前code列表: " + codeList.toString());
        System.out.println("连接加入！当前在线 " + getOnlineCount());
        // 设置订阅topic
        redisMessageListenerContainer.addMessageListener(subscribeListener, new ChannelTopic(coinCode));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("coinCode") String coinCode, @PathParam("sid") String sid) throws IOException {
        webSocketSet.remove(this);
        subOnlineCount();
        redisMessageListenerContainer.removeMessageListener(subscribeListener);

        // 前面已经remove了，这里重新统计已有的list，移除不再需要的code
        List<String> nowCodeList = getNowCodeList();
        codeList.stream().forEach(item->{
            // 移除不再本次统计结果中的code
            if(!nowCodeList.contains(item)){
                codeList.remove(item);
            }
        });
        System.out.println("当前code列表: " + codeList.toString());
        System.out.println("连接关闭！当前在线 " + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("coinCode") String coinCode, @PathParam("sid") String sid) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for (KLineDataServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, @PathParam("coinCode") String coinCode, @PathParam("sid") String sid, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public int getOnlineCount() {
        return onlineCount.get();
    }

    public void addOnlineCount() {
        KLineDataServer.onlineCount.getAndIncrement();
    }

    public void subOnlineCount() {
        KLineDataServer.onlineCount.getAndDecrement();
    }

    private List<String> getNowCodeList() {
        List<String> list = new ArrayList<>();
        KLineDataServer.webSocketSet.forEach(item -> {
            list.add(item.subscribeListener.getCode());
        });
        return list.stream().distinct().collect(Collectors.toList());
    }

}
