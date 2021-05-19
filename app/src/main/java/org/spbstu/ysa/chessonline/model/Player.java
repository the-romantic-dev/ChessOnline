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
        Set<Cell> pieces = this.opponentSetOfPieces();
        Set<Cell> allAllowedMoves = new HashSet<>();

        for (Cell cell : pieces) {
            Piece curPiece = cell.getPiece();
            if (curPiece.isWhite() == this.isWhite)
                allAllowedMoves.addAll(curPiece.getAllowedCells(cell, board));
        }


        return this.isCheck() && allAllowedMoves.isEmpty();
    }

    private Set<Cell> friendSetOfPieces() {
        Set<Cell> res = new HashSet();

        for (Cell[] column : board.getData()) {
            for (Cell cell : column) {
                Piece piece = cell.getPiece();
                if (piece != null && piece.isWhite() == this.isWhite) res.add(cell);
            }
        }
        return res;
    }

    private Set<Cell> opponentSetOfPieces() {
        Set<Cell> res = new HashSet();

        for (Cell[] column : board.getData()) {
            for (Cell cell : column) {
                Piece piece = cell.getPiece();
                if (piece != null && piece.isWhite() != this.isWhite) res.add(cell);
            }
        }
        return res;
    }
}

