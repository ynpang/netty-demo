package com.enjoy.chat.server.handler;

import com.enjoy.chat.process.IMProcessor;
import com.enjoy.chat.protocal.IMMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketHandler extends SimpleChannelInboundHandler<IMMessage> {

    private IMProcessor processor = new IMProcessor();

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, IMMessage msg) throws Exception {
        processor.process(ctx.channel(),msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接");
    }
}
