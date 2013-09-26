package com.test.tictactoeserver;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract class Packet {
    protected int id;
    public abstract void deserialize(ChannelBuffer buffer); 
    public abstract ChannelBuffer serialize(); 
    public static Packet getPacket(ChannelBuffer buffer) {
        Packet pckt;
        int idPckt = buffer.readInt();
        if(idPckt == 2)
            pckt = new PacketGetMoveAnswer();
        else 
            return null;
        pckt.deserialize(buffer);
        return pckt;
    }

}
