package com.test.tictactoeserver;

import org.jboss.netty.buffer.ChannelBuffer;

public class PacketGetMoveAnswer extends Packet {
    private int move;
    public int getMove() {
        return move;
    }
    @Override
    public void deserialize(ChannelBuffer buffer) {
        move = buffer.readInt();
    }

    @Override
    public ChannelBuffer serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
