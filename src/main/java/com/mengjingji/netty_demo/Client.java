package com.mengjingji.netty_demo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	String address;
	int port;
	public static void main(String[] args) throws InterruptedException {
		new Client("127.0.0.1",8080).start();
	}
	public Client(String address,int port) {
		this.address=address;
		this.port=port;
	}
	public  void start() throws InterruptedException {
		EventLoopGroup group=new NioEventLoopGroup();
		try{
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.remoteAddress(new InetSocketAddress(address, port));
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("myHandler",new ClientHandler());
				}

				
			});
			
			ChannelFuture future=bootstrap.connect().sync();
			future.addListener(new ChannelFutureListener() {
				
				public void operationComplete(ChannelFuture f) throws Exception {
					System.out.println("operationComplete...");
					if(f.isSuccess()){
						System.out.println("is success..");
					}else {
						System.out.println("is fail..");
					}
				}
			});
			future.channel().closeFuture().sync();
			
		}finally{
			group.shutdownGracefully().sync();
		}
	}
}
