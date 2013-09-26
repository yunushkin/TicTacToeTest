package com.test.tictactoeclient;

import org.jboss.netty.buffer.ChannelBuffer;

public class PacketGetMove extends Packet {
    PacketGetMove() {
    }
    @Override
    public void deserialize(ChannelBuffer buffer) {
        type = Board.Type.values()[buffer.readInt()];
        board = new Board();
        for(int col = 0 ; col < board.maxCol; col++) {
            for(int row = 0 ; row < board.maxRow; row++) {
                int type = buffer.readInt(); 
                if(type > 2)
                    return;
                board.set(row, col, Board.Type.values()[type]);
            }
        }
    }
    @Override
    public ChannelBuffer serialize() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Board getBoard() {
        return board;
    }
    public Board.Type getType() {
        return type;
    }
    private Board board;
    private Board.Type type;
}
