package com.test.tictactoeclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class Handler extends SimpleChannelUpstreamHandler {
    private Worker worker = new Worker();
    public Handler(){}
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        if (e.getMessage() instanceof ChannelBuffer) {
            Packet pckt = Packet.getPacket((ChannelBuffer)e.getMessage());
            worker.acceptPacket(pckt);
        }
        super.messageReceived(ctx, e);
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        worker.setCannel(ctx.getChannel());
        super.channelConnected(ctx, e);
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelClosed(ctx, e);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        //e.getCause().printStackTrace();
        System.out.println("exceptionCaught");
    }

}