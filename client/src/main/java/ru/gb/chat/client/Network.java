package ru.gb.chat.client;

import java.io.IOException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import ru.gb.Request;

public class Network {
    private final int FILE_SIZE = Integer.MAX_VALUE;
    private final int PORT = 29894;
    private final String HOSTNAME = "localhost";
    private Channel channel;

    public Network(){
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                Bootstrap boot = new Bootstrap();
                boot.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                socketChannel.pipeline().addLast(
                                        new ObjectDecoder(FILE_SIZE, ClassResolvers.weakCachingResolver(Network.class.getClassLoader())),
                                        new ObjectEncoder(),
                                        new ClientHandler());
                            }
                        });
                ChannelFuture future = boot.connect(HOSTNAME,PORT);
                channel = future.channel();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }




    public void close() throws IOException {
        channel.close();
    }

    public void sendFiles(Request request){
        try {
            channel.writeAndFlush(request).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
