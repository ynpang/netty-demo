package com.enjoy.chat.server.handler;

import com.enjoy.chat.process.IMProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    private IMProcessor processor = new IMProcessor();
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {

        processor.process(channelHandlerContext.channel(),msg.text());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        processor.logout(ctx.channel());
    }
}
