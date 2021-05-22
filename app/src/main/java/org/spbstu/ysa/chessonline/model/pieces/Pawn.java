package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    public final int BORDER_COORDINATE = this.isWhite() ? 7 : 0;
    public final Cell startCell;
    public boolean isPassantAvailable = false;

    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite);
        startCell = new Cell(x, y);
    }

    public Pawn(boolean color, Cell cell) {
        super(color);
        startCell = cell;
    }


    @Override
    public Set<Cell> getAllowedCells(Cell cell, Board boardClass) {
        int x = cell.getX();
        int y = cell.getY();
        Set<Cell> res = new HashSet<>();

        Cell[][] board = boardClass.getData();

        int i = this.isWhite() ? 1 : -1;
        int curY = y + i;
        if (curY >= 0 && curY < 8 && board[curY][x].getPiece() == null) {
            res.add(new Cell(x, curY));
            if (x == startCell.getX() && y == startCell.getY() && board[curY + i][x].getPiece() == null)
                res.add(new Cell(x, curY + i));
        }
        // check if Pawn can catch opponent piece from RIGHT cell
        int curX = x + 1;
        if (curY >= 0 && curY < 8  && curX < 8) {
            Piece oppositePiece = board[curY][curX].getPiece();
            if (oppositePiece != null) {
                if (oppositePiece.isWhite() != this.isWhite()) res.add(new Cell(curX, curY));
            } else {
                Piece checkingPiece = board[y][curX].getPiece();
                if (checkingPiece != null && checkingPiece.getName().equals("Pawn")) {
                    Pawn checkingPawn = (Pawn) checkingPiece;
                    if (checkingPawn.isPassantAvailable && this.isWhite() != checkingPawn.isWhite())
                        res.add(new Cell(curX, curY));
                }
            }


        }
        // check if Pawn can catch opponent piece from LEFT cell
        curX = x - 1;
        if (curY >= 0 && curY < 8 && curX >= 0) {
            Piece oppositePiece = board[curY][curX].getPiece();
            if (oppositePiece != null) {
                if (oppositePiece.isWhite() != this.isWhite()) res.add(new Cell(curX, curY));
            } else {
                Piece checkingPiece = board[y][curX].getPiece();
                if (checkingPiece != null && checkingPiece.getName().equals("Pawn")) {
                    Pawn checkingPawn = (Pawn) checkingPiece;
                    if (checkingPawn.isPassantAvailable && this.isWhite() != checkingPawn.isWhite())
                        res.add(new Cell(curX, curY));
                }
            }

        }
        return res;
    }

    @Override
    public Piece clone() throws CloneNotSupportedException {
        return new Pawn(isWhite(),startCell);
    }
    @Override
    public String getName() {
        return "Pawn";
    }
}
