package com.test.tictactoeserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;

public class TicTacToe {
    /**
     * @param args the command line arguments
     */
    public static ServerBootstrap bootstrap;
    
    public static void netInit() {
        
        //channelGroup = new DefaultChannelGroup(id + "-all-channels");
    }
    
    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8086;
        if(args.length > 0) 
            host = new String(args[0]);
        if(args.length > 1)
            port = Integer.parseInt(args[1]);
        Server server = new Server(host, port);
        if(!server.start())
            System.err.println("Server not started!!!");
        while(true) {
            Thread.sleep(1000);
        } 
        // TODO code application logic here
    }
}
