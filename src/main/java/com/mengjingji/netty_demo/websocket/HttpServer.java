package com.mengjingji.netty_demo.websocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServer {
	int port;
	public HttpServer(int port) {
		this.port=port;
	}
	public void run() throws InterruptedException {
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup worker=new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(boss, worker);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.localAddress(port);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("decoder", new HttpRequestDecoder());
					ch.pipeline().addLast("encoder", new HttpResponseEncoder());
					ch.pipeline().addLast("handler",new HttpServerChannelHandler2());
				}
			});
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			ChannelFuture future=bootstrap.bind().sync();
		}finally{};
	}
	public static void main(String[] args) throws InterruptedException {
		new HttpServer(80).run();
		
	}

}
