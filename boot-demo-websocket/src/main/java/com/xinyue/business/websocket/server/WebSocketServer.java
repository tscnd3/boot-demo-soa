package com.xinyue.business.websocket.server;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyue.business.websocket.utils.SpringUtils;
import com.xinyue.framework.redis.util.RedisUtil;


/**
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/imserver/{userId}")
@Component
public class WebSocketServer {

    static Log log=LogFactory.getLog(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
//    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    
    
    private static RedisUtil redisUtil=SpringUtils.getBean(RedisUtil.class);

    
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";
    
    private final static String SOCKET_KEY="im:socket_hash";
    
    private final static String SOCKET_ID="socket_id:%s";
    
    private final static String ONLINE_USER_SET="online_user_set";
    
    

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);  //加入set中
        }else{
            webSocketMap.put(userId,this); //加入set中
        }
        redisUtil.set(ONLINE_USER_SET, userId);//设置在线列表
        log.info("用户连接:"+userId+",当前在线人数为:" +redisUtil.sGetSetSize(ONLINE_USER_SET));
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
//        if(webSocketMap.containsKey(userId)){
//            webSocketMap.remove(userId);
//            //从set中删除
//            onlineCount.decrementAndGet();
//        }
    	String socketId=String.format(SOCKET_ID,userId);
    	if(redisUtil.hasKey(socketId)){
    		redisUtil.hdel(SOCKET_KEY, socketId);
//          onlineCount.decrementAndGet();
    	}
//        log.info("用户退出:"+userId+",当前在线人数为:" + onlineCount.get());
    	 log.info("用户退出:"+userId+",当前在线人数为:" + redisUtil.hlen(SOCKET_KEY));
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                
               String userId=session.getPathParameters().get("userId");
                
                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("toUserId");
                String sendSocketId=String.format(SOCKET_ID, userId);
                String receiveSocketId=String.format(SOCKET_ID, toUserId);
                //传送给对应toUserId用户的websocket
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
//                }else{
//                    log.error("请求的userId:"+toUserId+"不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
                if(redisUtil.hHasKey(SOCKET_KEY,(receiveSocketId))){
                	WebSocketServer wSocketServer=(WebSocketServer)redisUtil.hget(SOCKET_KEY,receiveSocketId);
                	wSocketServer.sendMessage(jsonObject.toJSONString());
                }else{
                	WebSocketServer wSocketServer=(WebSocketServer)redisUtil.hget(SOCKET_KEY,sendSocketId);
                	wSocketServer.sendMessage("请求的userId:"+userId+"不在该服务器上");
                	log.error("请求的userId:"+userId+"不在该服务器上");
//                  //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
    	String socketId=String.format(SOCKET_ID,userId);
//        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
//            webSocketMap.get(userId).sendMessage(message);
//        }else{
//            log.error("用户"+userId+",不在线！");
//        }
        if(redisUtil.hHasKey(SOCKET_KEY,socketId)){
        	WebSocketServer webSocketServer=(WebSocketServer) redisUtil.hget(SOCKET_KEY,socketId);
        	webSocketServer.sendMessage(message);
	     }else{
	          log.error("用户"+userId+",不在线！");
	     }
    }



  
}

