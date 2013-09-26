package com.test.tictactoeserver;

import java.util.List;
import java.util.ArrayList;
import org.jboss.netty.channel.Channel;
import java.util.ListIterator;

public class GamePlay {
    class Games {
        public Games(Channel channelX_, Channel channelO_) {
            channelX = channelX_;
            channelO = channelO_;
            game = new Game();
        }
        public Channel channelX;
        public Channel channelO;
        public Game game; 
    }
    
    public static final GamePlay INSTANCE = new GamePlay();
    
    private GamePlay() { 
        list = new ArrayList<Games>();
    }
    
    public void acceptPacket(Channel channel, Packet pckt) {
        Games game = findGame(channel);
        if(game == null) {
            return;
        }
        PacketGetMoveAnswer moveAnswer = (PacketGetMoveAnswer)pckt;
        game.game.move(moveAnswer.getMove());
        if(game.game.getState() == Game.State.moveO) {
            PacketGetMove pcktMove = new PacketGetMove(game.game.getBoard(), Board.Type.O);
            game.channelO.write(pcktMove.serialize());
        } else {
            if(game.game.getState() == Game.State.moveX) {
                PacketGetMove pcktMove = new PacketGetMove(game.game.getBoard(), Board.Type.X);
                game.channelX.write(pcktMove.serialize());
            }
            if(game.game.getState() == Game.State.winO){
                game.channelX.write(new PacketResult(PacketResult.Result.loss).serialize());
                game.channelO.write(new PacketResult(PacketResult.Result.win).serialize());
            }
            if(game.game.getState() == Game.State.winX){
                game.channelO.write(new PacketResult(PacketResult.Result.loss).serialize());
                game.channelX.write(new PacketResult(PacketResult.Result.win).serialize());
            }
            if(game.game.getState() == Game.State.draw){
                game.channelO.write(new PacketResult(PacketResult.Result.draw).serialize());
                game.channelX.write(new PacketResult(PacketResult.Result.draw).serialize());
            }
        }
    }
    private Games findGame(final Channel channel) {
        ListIterator  it = list.listIterator();
        Games pair;
        while(it.hasNext()) {
            pair = (Games) it.next();
            if(pair.channelO == channel || pair.channelX == channel)  {
                return pair;
            }
        }
        return null;
    }
    public void beginNewGame(final Games game) {
        game.game.newGame();
        PacketGetMove pcktMove = new PacketGetMove(game.game.getBoard(), Board.Type.O);
        game.channelO.write(pcktMove.serialize());
    }
    public Channel getEnemyChannel(final Channel channel) {
        Games pair = findGame(channel);
        if(pair.channelO == channel) {
            return pair.channelX;
        } else {
            return pair.channelO;
        }
    }
    public void addPlayer(Channel channel) {
        Games pair = findGame(null);
        if(pair != null) {
            if(pair.channelO == null) {
                pair.channelO = channel;
            } else {
                pair.channelX = channel;
            }
            beginNewGame(pair);
        } else {
            list.add(new Games(null, channel));
        }
    }
    public void deletePlayers(Channel channel) {
        Games pair = findGame(channel);
        if(pair == null) {
            return;
        }
        Channel chX = pair.channelX;
        Channel chO = pair.channelO;
        list.remove(pair);
    }
    
    private List<Games> list;
}
