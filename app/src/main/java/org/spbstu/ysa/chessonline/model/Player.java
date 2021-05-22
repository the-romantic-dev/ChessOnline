package org.spbstu.ysa.chessonline.model;

import java.util.Set;

public class Player {
    private boolean isWhite;
    private Board board = new Board();
    private Cell currentCell = null;
    /*    private Set<Cell> allowedMoves = null;*/
    private boolean isWhitesTurn = true;


    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Board getBoard() {
        return this.board;
    }

    public Set<Cell> capturePiece(Cell cell) {
        return board.capturePiece(cell);
    }

    public Set<Cell> putPiece(Cell cell) {
        return board.putPiece(cell);
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
        board.setCurrentCell(currentCell);
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

    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    public boolean isThisPlayersTurn() {
        return isWhitesTurn == isWhite;
    }

    public void changeTurn() {
        isWhitesTurn = !isWhitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.isWhitesTurn = whitesTurn;
    }

    public boolean isCheck() {
        return board.isCheck(this.isWhite);
    }


    public boolean isCheckmate() {
        return board.isCheckmate(isWhite);
    }

}

