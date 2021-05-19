package org.spbstu.ysa.chessonline.model;

import org.spbstu.ysa.chessonline.model.pieces.*;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private boolean isWhite;
    private Board board = new Board();
    private Cell currentCell = null;
    private Set<Cell> allowedMoves = null;
    private boolean turn = true;


    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Cell[][] getBoard() {
        return this.board.getData();
    }

    public Set<Cell> capturePiece(Cell cell) {
        return board.capturePiece(cell);
    }

    public boolean putPiece(Cell cell) {
        return board.putPiece(cell);
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setColor(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isCheck() {
        return board.isCheck(this.isWhite);
    }


    public boolean isCheckmate() {
      return board.isCheckmate(isWhite);
    }

}

