package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    private boolean isMoved = false;

    public Rook(boolean isWhie) {
        super(isWhie);
    }

    @Override
    public Set<Cell> getAllowedCells(Cell cell, Board boardClass) {

        int x = cell.getX();
        int y = cell.getY();

        Set<Cell> allowedMoves = new HashSet<>();

        Cell[][] board = boardClass.getData();

        for (int i = x + 1; i < 8; i++) {
            Piece curPiece = board[y][i].getPiece();
            if (curPiece == null) allowedMoves.add(new Cell(i, y));
            else {
                if (curPiece.isWhite() != this.isWhite()) allowedMoves.add(new Cell(i, y));
                break;
            }
        }


        for (int i = x - 1; i >= 0; i--) {
            Piece curPiece = board[y][i].getPiece();
            if (curPiece == null) allowedMoves.add(new Cell(i, y));
            else {
                if (curPiece.isWhite() != this.isWhite()) allowedMoves.add(new Cell(i, y));
                break;
            }
        }


        for (int i = y + 1; i < 8; i++) {
            Piece curPiece = board[i][x].getPiece();
            if (curPiece == null) allowedMoves.add(new Cell(x, i));
            else {
                if (curPiece.isWhite() != this.isWhite()) allowedMoves.add(new Cell(x, i));
                break;
            }
        }


        for (int i = y - 1; i >= 0; i--) {
            Piece curPiece = board[i][x].getPiece();
            if (curPiece == null) allowedMoves.add(new Cell(x, i));
            else {
                if (curPiece.isWhite() != this.isWhite()) allowedMoves.add(new Cell(x, i));
                break;
            }
        }

        Set<Cell> res = new HashSet();

        return allowedMoves;
    }


    @Override
    public String getName() {
        return "Rook";
    }

    public boolean isMoved() {
        return isMoved;
    }

    @Override
    public Piece clone() throws CloneNotSupportedException {
        return new Rook(isWhite());
    }

    public void setMoved() {
        isMoved = true;
    }
}
