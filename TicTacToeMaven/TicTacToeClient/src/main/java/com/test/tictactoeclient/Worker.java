package com.test.tictactoeclient;

import java.io.IOException;
import org.jboss.netty.channel.Channel;

public class Worker {
    private Channel channel;
    private GUI gui = new GUI();
    void acceptPacket(Packet pckt) throws IOException, InterruptedException {
        if(pckt instanceof PacketGetMove) {
            PacketGetMove pcktGetMove = (PacketGetMove)pckt;
            int move = gui.getMove(pcktGetMove.getBoard(), (pcktGetMove.getType() == Board.Type.O) ? true : false);
            channel.write(new PacketGetMoveAnswer(move).serialize());
        } 
        if(pckt instanceof PacketResult) {
            PacketResult pcktResult = (PacketResult)pckt;
            gui.result(pcktResult.getResult());
            channel.disconnect();
            channel.close();
            gui.waitExit();
            System.exit(0);
        }
    }
    void setCannel(Channel channel_) {
        channel = channel_;
    }
}
