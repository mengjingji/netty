package com.mengjingji.netty_demo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
	public void channelRead(ChannelHandlerContext context,Object msg) {
		System.out.println("msg comming...:"+msg);
		context.write(msg);
	}
	
	public void channelReadComplete(ChannelHandlerContext context) {
		System.out.println("finish Read on server...");
		context.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	public void exceptionCaught(ChannelHandlerContext context,Throwable throwable) {
		throwable.printStackTrace();
		System.out.println("error caught by meng;");
		context.close();
	}
}
