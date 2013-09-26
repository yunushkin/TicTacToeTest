package com.test.tictactoeserver;

public class Game {
    enum State{
        winX,
        winO,
        draw,
        moveO,
        moveX
    }
    private State state;
    int scoreX;
    int scoreO;
    private int step;
    private Board board = new Board();
    public Game() {
        step = 0;
        scoreO = 0;
        scoreX = 0;
        state = State.moveO;
    }
    private int place2row(int place){
        return place/board.maxRow;
    }
    private int place2col(int place){
        return place%board.maxRow;
    }
    private State checkWinner() {
        if(step < 4) 
            return State.moveO;
        if(((board.get(0, 0) == board.get(1, 1) && board.get(0, 0) == board.get(2, 2)) ||
           (board.get(0, 2) == board.get(1, 1) && board.get(0, 2) == board.get(2, 0))) && board.get(1, 1) != Board.Type.NOT) {
            if(board.get(1, 1) == Board.Type.O) 
                return State.winO;
            return State.winX;
        } else {
            for(int i = 0; i < board.maxRow; i++) {
                if(board.get(i, 0) == board.get(i, 1) && board.get(i, 0) == board.get(i, 2) && board.get(i, 0) != Board.Type.NOT) {
                    if(board.get(i, 0) == Board.Type.O) 
                        return State.winO;
                    return State.winX;
                }
                if(board.get(0, i) == board.get(1, i) && board.get(0, i) == board.get(2, i) && board.get(0, i) != Board.Type.NOT) {
                    if(board.get(0, i) == Board.Type.O) 
                        return State.winO;
                    return State.winX;
                }    
            }
        }
        if(step >= 8)
           return State.draw;
        return State.moveO;
    }
    public boolean move(int place) {
        if(place < 0 &&  place > 8) {
            return false;
        }
        int row = place2row(place);
        int col = place2col(place);
        if(board.get(row, col) != Board.Type.NOT)
            return false;
        Board.Type type = Board.Type.O;
        if(step%2 == 0) {
            state = State.moveX;
        } else {
            type = Board.Type.X;
            state = State.moveO;
        }
        board.set(row, col, type);
        State winner = checkWinner();
        if(winner == State.moveO){
            step++;
        } else {
            state = winner;
        }
        return true;
    }
    public void newGame() {
        step = 0;
        board.clear();
        state = State.moveO;
    }
    public State getState() {
        return state;
    } 
    Board getBoard() {
        return board;
    }
}
