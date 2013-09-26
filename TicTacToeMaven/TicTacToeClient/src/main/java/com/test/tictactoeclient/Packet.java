package com.test.tictactoeclient;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract class Packet {
    protected int id;
    public abstract void deserialize(ChannelBuffer buffer); 
    public abstract ChannelBuffer serialize(); 
    public static Packet getPacket(ChannelBuffer buffer) {
        Packet pckt;
        int id = buffer.readInt();
        switch ( id ) {
            case 1:
                pckt = new PacketGetMove();
            break;    
            case 3:
                pckt = new PacketResult();
            break;
            default: return null;
        }
        pckt.deserialize(buffer);
        return pckt;
    }
}
