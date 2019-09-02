package com.enjoy.chat.protocal;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;

@Getter
@Setter
@Message
public class IMMessage {

    private String addr;
    private String cmd;
    private long time;
    private int online;
    private String sender;
    private String receiver;
    private String content;

    public IMMessage(){}

    public IMMessage(String cmd,long time,int online,String content){
        this.cmd = cmd;
        this.time = time;
        this.online = online;
        this.content = content;
    }

    public IMMessage(String cmd,long time,String sender){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
    }

    public IMMessage(String cmd,long time,String sender,String content){
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.content = content;
    }

}
