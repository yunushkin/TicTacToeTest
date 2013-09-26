package com.test.tictactoeclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class PacketGetMoveAnswer extends  Packet {
    private int move;
    PacketGetMoveAnswer(int move_) {
        move = move_;
        id = 2;
    }
    @Override
    public void deserialize(ChannelBuffer buffer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public ChannelBuffer serialize() {
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        buffer.writeInt(id);
        buffer.writeInt(move);
        return buffer;
    }
}
