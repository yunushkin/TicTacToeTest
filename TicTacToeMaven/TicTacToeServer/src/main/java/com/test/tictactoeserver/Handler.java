package com.test.tictactoeserver;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class Handler extends SimpleChannelUpstreamHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        if (e.getMessage() instanceof ChannelBuffer) {
             Packet pckt = Packet.getPacket((ChannelBuffer)e.getMessage());
             GamePlay.INSTANCE.acceptPacket(ctx.getChannel(), pckt);
         }
        super.messageReceived(ctx, e);
    }
    
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
       System.out.println("channelConnected");
       GamePlay.INSTANCE.addPlayer(ctx.getChannel());
       super.channelConnected(ctx, e);
    }
    
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        Channel enemy = GamePlay.INSTANCE.getEnemyChannel(ctx.getChannel());
        if(enemy != null) {
            enemy.close();
        }
        super.channelClosed(ctx, e);
        GamePlay.INSTANCE.deletePlayers(ctx.getChannel());
    }
     @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        //e.getCause().printStackTrace();
        System.out.println("exceptionCaught");
    }
    /*@Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
            throws Exception {
        super.writeComplete(ctx, e);
        this.writtenBytes.addAndGet(e.getWrittenAmount());
    }
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        super.channelClosed(ctx, e);
        System.out.println(this.id + ctx.getChannel() + " -> sent: " +
                           this.getWrittenBytes() + "b, recv: " +
                           this.getReadBytes() + "b");
    }*/
}
