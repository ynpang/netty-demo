package com.enjoy.chat.process;

import com.enjoy.chat.protocal.IMDecoder;
import com.enjoy.chat.protocal.IMEncoder;
import com.enjoy.chat.protocal.IMMessage;
import com.enjoy.chat.protocal.IMP;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.simple.JSONObject;

public class IMProcessor {

    private final static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private IMDecoder decoder = new IMDecoder();
    private IMEncoder encoder = new IMEncoder();

    private final AttributeKey<String> NICK_NAME = AttributeKey.valueOf("nickName");
    private final AttributeKey<String> IP_ADDR = AttributeKey.valueOf("ipAddr");
    private final AttributeKey<String> ATTRS = AttributeKey.valueOf("attrs");

    public void logout(Channel client){
        onlineUsers.remove(client);
    }

    public void process(Channel client,IMMessage msg){
        process(client,encoder.encode(msg));
    }

    public void process(Channel client,String msg){
        IMMessage request = decoder.decode(msg);
        if(null == request){return;}

        String nickName = request.getSender();

        if(IMP.LOGIN.getName().equals(request.getCmd())){

            client.attr(NICK_NAME).getAndSet(request.getSender());
            //client.attr(IP_ADDR).getAndSet(request.getAddr());

            onlineUsers.add(client);

            for(Channel channel : onlineUsers){
                if(channel != client){
                    request = new IMMessage(IMP.SYSTEM.getName(),systemTime(),onlineUsers.size(), nickName + "加入聊天室");
                }else{
                    request = new IMMessage(IMP.SYSTEM.getName(),systemTime(),onlineUsers.size(), "已与服务器建立连接");
                }
                String text = encoder.encode(request);
                channel.writeAndFlush(new TextWebSocketFrame(text));
            }
        }else if(IMP.LOGOUT.getName().equals(request.getCmd())){
            onlineUsers.remove(client);
        }else if(IMP.CHAT.getName().equals(request.getCmd())){
            for(Channel channel: onlineUsers){
                if(channel != client){
                    request.setSender(client.attr(NICK_NAME).get());
                }else{
                    request.setSender("you");
                }
                String text = encoder.encode(request);
                channel.writeAndFlush(new TextWebSocketFrame(text));
            }
        }else if(IMP.FLOWER.getName().equals(request.getCmd())){
        }
    }

    private long systemTime(){
        return System.currentTimeMillis();
    }
}
