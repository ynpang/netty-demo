package com.enjoy.chat.client.handler;

import com.enjoy.chat.protocal.IMMessage;
import com.enjoy.chat.protocal.IMP;
import com.sun.istack.internal.logging.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.util.Scanner;

public class ChatClientHandler extends ChannelInboundHandlerAdapter{

    private static Logger LOG = Logger.getLogger(ChatClientHandler.class);
    private ChannelHandlerContext ctx;
    private String nickName;

    public ChatClientHandler(String nickName) {
        this.nickName = nickName;
    }

    private void session() throws IOException{
        new Thread(){
          public void run(){
              LOG.info(nickName + "你好，请在控制台输入消息内容");
              IMMessage message = null;
              Scanner scanner = new Scanner(System.in);
              do{
                  if(scanner.hasNext()){
                      String input = scanner.nextLine();
                      if("exit".equals(input)){
                          message = new IMMessage(IMP.LOGOUT.getName(),System.currentTimeMillis(),nickName);
                      }else{
                          message = new IMMessage(IMP.CHAT.getName(),System.currentTimeMillis(),nickName,input);
                      }
                  }
              }
              while(sendMsg(message));
              scanner.close();;
          }
        }.start();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        IMMessage message = new IMMessage(IMP.LOGIN.getName(),System.currentTimeMillis(),this.ctx.name());
        sendMsg(message);
        LOG.info("成功连接服务器，已执行登录动作");
        session();
    }

    private boolean sendMsg(IMMessage msg) {
        ctx.channel().writeAndFlush(msg);
        LOG.info("已发送至聊天面板，请继续输入");
        return msg.getCmd().equals(IMP.LOGOUT) ? false : true;
    }

}
