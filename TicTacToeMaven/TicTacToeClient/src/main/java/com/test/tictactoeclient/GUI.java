package com.test.tictactoeclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI {
    private void showBoard(Board board) {
        int pos = 0;
        for(int row = 0; row < board.maxRow; row++) {
            System.out.print("|");
            for(int col = 0; col < board.maxCol; col++) {
                pos++;
                if(board.get(row, col) == Board.Type.O) {
                    System.out.print("O|");
                } else {
                    if(board.get(row, col) == Board.Type.X) {
                        System.out.print("X|");
                    } else {
                        System.out.print(pos);
                        System.out.print("|");
                    } 
                }
            }
            System.out.println("");
        }
    }
    public int getMove(Board board, boolean isO) {
        Board.Type type = Board.Type.O;
        boolean error;
        if(!isO) {
            type = Board.Type.X;
            System.out.println("You move X...");
        } else {
            System.out.println("You move O...");
        }
        showBoard(board);
        int move = 0;
        do {
            error = false;
            System.out.print("Enter the number(1-9): ");
            DataInputStream in = new DataInputStream(System.in);
            try {
                move = Integer.parseInt(in.readLine()) - 1;
            } catch (IOException ex) {
                error = true;
            }
            try{
                board.set(move/3, move%3, type);
            }catch(IllegalAccessError err){
                error = true;
            }
        } while(error == true);
        showBoard(board);
        System.out.println("Wait another player's turn...");        
        return move;
    }
    public void result(PacketResult.Result result) {
        if(result == PacketResult.Result.win) {
            System.out.println("You win...");
        } else {
            if(result == PacketResult.Result.loss) {
                System.out.println("You loss...");
            } else {
                System.out.println("Draw...");
            }
        }
    }
    public void waitExit() {
        System.out.println("Press eny key to exit...");
        DataInputStream in = new DataInputStream(System.in);
        try {
            in.readByte();
        } catch (IOException ex) {
            return;
        }
    }
}
