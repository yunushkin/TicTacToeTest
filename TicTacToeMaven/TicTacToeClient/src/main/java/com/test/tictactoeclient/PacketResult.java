package com.test.tictactoeclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class PacketResult extends Packet {
    enum Result {
        win,
        loss,
        draw
    };
    private Result result;
    public Result getResult() {
        return result;
    }
    PacketResult() {
        id = 3;
    }
    @Override
    public ChannelBuffer serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void deserialize(ChannelBuffer buffer) {
        result = Result.values()[buffer.readInt()];
    }
}
