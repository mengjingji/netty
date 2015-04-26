package com.mengjingji.netty_demo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Hello world!
 *
 */
public class App 
{
	int port;
	public App(int port) {
		this.port=port;
	}
	public void start() throws InterruptedException {
		
		EventLoopGroup listenGroup=new NioEventLoopGroup(50);
		EventLoopGroup workGroup=new NioEventLoopGroup(50);
		
		try{
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(listenGroup,workGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.localAddress(new InetSocketAddress(port));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel >() {

				@Override
				protected void initChannel(SocketChannel  ch) throws Exception {
					ch.pipeline().addLast("myHandler",new ServerHandler());
				}
			});
			
			ChannelFuture future=bootstrap.bind().sync();
			future.channel().closeFuture().sync();
		}finally{
			listenGroup.shutdownGracefully().sync();
			workGroup.shutdownGracefully().sync();
			System.out.println("服务器优雅的释放了线程资源并退出服务...");
		}
		
	}
    public static void main( String[] args ) throws InterruptedException
    {
    	System.out.println( "server starting...!" );
    	new App(8080).start();
    	System.out.println( "server started...!" );
    }
}
