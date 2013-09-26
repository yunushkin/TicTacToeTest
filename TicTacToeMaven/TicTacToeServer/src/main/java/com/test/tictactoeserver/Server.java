package com.test.tictactoeserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

public class Server {
    private String host;
    private int port;
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup;
    private final int sizeBossPool = 1;
    private final int sizeWorkerPool = 4;
    public Server(String host_, int port_)  {
        host = host_;
        port = port_;
    }
    public boolean start() {
        Executor bossPool = Executors.newFixedThreadPool(sizeBossPool);
        Executor workerPool = Executors.newFixedThreadPool(sizeWorkerPool);

        ChannelFactory factory = new NioServerSocketChannelFactory(bossPool, workerPool);
        bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new Decoder(), new Encoder(), new Handler());//new PlayerHandler(decoder, encoder));
            }
        });
        channelGroup = new DefaultChannelGroup("Tic-Tac-Toe-Server" + "-all-channels");
        
        InetSocketAddress address = new InetSocketAddress(host, port); 
        //можно указать только порт и тогда будет слушать на всех интерфейсазх
        Channel acceptor = bootstrap.bind(address);   
        if (acceptor.isBound()) {
            System.err.println("+++ SERVER - bound to ...");
            channelGroup.add(acceptor);
            return true;
        } else {
            System.err.println("+++ SERVER - Failed to bind to ...");
            this.bootstrap.releaseExternalResources();
            return false;
        }
    }
    public void stop() {
        channelGroup.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }
}
