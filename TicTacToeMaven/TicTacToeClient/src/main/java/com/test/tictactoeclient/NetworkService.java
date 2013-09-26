package com.test.tictactoeclient;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class NetworkService {
    public Channel getChannel(){
        return channel;
    }
    private Channel channel;
    private String host;
    private int port;
    private ClientBootstrap bootstrap;
    public NetworkService() {
        host = "127.0.0.1";
        port = 8086;
    }
    public NetworkService(String host_, int port_) {
        host = host_;
        port = port_;
    }
    public void connect() throws Exception {
        Executor bossPool = Executors.newFixedThreadPool(3);
        Executor workerPool = Executors.newFixedThreadPool(3);

        ChannelFactory factory = new NioClientSocketChannelFactory(bossPool, workerPool);
        bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new Decoder(), new Encoder(), new Handler());//new PlayerHandler(decoder, encoder));
            }
        });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        future.awaitUninterruptibly();
        if (!future.isSuccess()) {
            throw new Exception();
        }
        channel = future.getChannel();
        //можно указать только порт и тогда будет слушать на всех интерфейсах
        
    }
}
