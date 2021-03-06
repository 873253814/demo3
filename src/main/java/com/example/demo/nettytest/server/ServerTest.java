//package com.example.demo.nettytest.server;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import nettytest.server.handler.HttpServerInitializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.net.InetSocketAddress;
//
//@Component
//public class ServerTest {
//
//
//    private Integer port = 8888;
//
//    @Autowired
//    private HttpServerInitializer httpServerInitializer;
//
//    /**
//     * boss 线程组，用于服务端接受客户端的连接
//     */
//    private EventLoopGroup bossGroup = new NioEventLoopGroup();
//    /**
//     * worker 线程组，用于服务端接受客户端的数据读写
//     */
//    private EventLoopGroup workerGroup = new NioEventLoopGroup();
//    /**
//     * Netty Server Channel
//     */
//    private Channel channel;
//
//    /**
//     * 启动 Netty Server
//     */
//    @PostConstruct
//    public void start() throws InterruptedException {
//        // 创建 ServerBootstrap 对象，用于 Netty Server 启动
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        // 设置 ServerBootstrap 的各种属性
//        bootstrap.group(bossGroup, workerGroup) // 设置两个 EventLoopGroup 对象
//                .channel(NioServerSocketChannel.class)  // 指定 Channel 为服务端 NioServerSocketChannel
//                .localAddress(new InetSocketAddress(port)) // 设置 Netty Server 的端口
//                .option(ChannelOption.SO_BACKLOG, 1024) // 服务端 accept 队列的大小
//                .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
//                .childOption(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
//                .childHandler(httpServerInitializer);
//        // 绑定端口，并同步等待成功，即启动服务端
//        ChannelFuture future = bootstrap.bind().sync();
//        if (future.isSuccess()) {
//            channel = future.channel();
//        }
//    }
//
//    /**
//     * 关闭 Netty Server
//     */
//    @PreDestroy
//    public void shutdown() {
//        // 关闭 Netty Server
//        if (channel != null) {
//            channel.close();
//        }
//        // 优雅关闭两个 EventLoopGroup 对象
//        bossGroup.shutdownGracefully();
//        workerGroup.shutdownGracefully();
//    }
//
//
////    public static void main(String[] args) {
////        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
////        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
////
////        try {
////            ServerBootstrap bootstrap = new ServerBootstrap();
////
////            bootstrap.group(bossGroup, workerGroup)
////                    .channel(NioServerSocketChannel.class)
////                    .childHandler(new HttpServerInitializer());
////            ChannelFuture future = bootstrap.bind(8080).sync();
////            future.channel().closeFuture().sync();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        } finally {
////            bossGroup.shutdownGracefully();
////            workerGroup.shutdownGracefully();
////        }
////    }
//}
