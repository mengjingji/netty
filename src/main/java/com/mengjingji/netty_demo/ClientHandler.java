package com.mengjingji.netty_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	@Override
	public void channelRead0(ChannelHandlerContext ctx,ByteBuf in) {
		System.out.println("get byte from server...:"+ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
	}
	
	 /**
	   *捕捉到异常 
	   * */
	  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	    cause.printStackTrace();
	    ctx.close();
	  }

}
