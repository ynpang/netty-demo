package com.enjoy.chat.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private URL baseURL = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
    private final String WEB_ROOT = "webroot";

    private File getFileFromRoot(String fileName) throws URISyntaxException {
        String path = baseURL.toURI() + WEB_ROOT + "/" + fileName;
        path = !path.startsWith("file:") ? path : path.substring(5);
        path.replaceAll("//","/");
        return new File(path);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.getUri();

        String page = uri.equals("/") ? "chat.html" : uri;
        RandomAccessFile file = new RandomAccessFile(getFileFromRoot(page),"r");
        String contextType = "text/html;";

        if(uri.endsWith(".css")){
            contextType= "text/css;";
        }else if(uri.endsWith(".js")){
            contextType = "text/javascript;";
        }else if(uri.toLowerCase().matches("(jpg|png|gif)$")){
            String ext = uri.substring(uri.lastIndexOf("."));
            contextType= "image/" + ext + ";";
        }

        HttpResponse response = new DefaultHttpResponse(fullHttpRequest.getProtocolVersion(), HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,contextType + "charset=utf-8;");

        boolean keepAlive = HttpHeaders.isKeepAlive(fullHttpRequest);
        if(keepAlive){
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
            response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }

        channelHandlerContext.write(response);
        channelHandlerContext.write(new DefaultFileRegion(file.getChannel(),0,file.length()));

        ChannelFuture f = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!keepAlive){
            f.addListener(ChannelFutureListener.CLOSE);
        }
        file.close();
    }
}
