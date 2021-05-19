package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    private final int BORDER_COORDINATE = this.isWhite() ? 7 : 1;
    private Cell startCell;

    public Pawn(boolean color, int x, int y) {
        super(color);
        startCell = new Cell(x, y);
    }


    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        Set<Cell> res = new HashSet<>();
        int i = this.isWhite() ? 1 : -1;
        int curY = y + i;
        if (curY >= 0 && curY < 8 && board[curY][x].getPiece() == null) {
            res.add(new Cell(x, curY));
            if (x == startCell.getX() && y == startCell.getY() && board[curY + i][x].getPiece() == null)
                res.add(new Cell(x, curY + i));
        }
        // check if Pawn can catch opponent piece from RIGHT cell
        int curX = x + 1;
        if (curY >= 0 && curY < 8 && curX >= 0 && curX < 8) {
            Piece oppositePiece = board[curY][curX].getPiece();
            if (oppositePiece != null)
                if (oppositePiece.isWhite() != this.isWhite()) res.add(new Cell(curX, curY));
        }
        // check if Pawn can catch opponent piece from LEFT cell
        curX = x - 1;
        if (curY >= 0 && curY < 8 && curX >= 0 && curX < 8) {
            Piece oppositePiece = board[curY][curX].getPiece();
            if (oppositePiece != null)
                if (oppositePiece.isWhite() != this.isWhite()) res.add(new Cell(curX, curY));
        }


        //реализация на проходе:
        // проверить есть клетка по диагонали
        // если есть то передавать и забыть
        // если нет то проверять НА ПРОХОДЕ
        // достать фигуру с боку проверить у неё предыдущее поле(precious Cell) и проверить равно ли оно стартовому
        // если да то срубить

        return res;
    }


    @Override
    public String getName() {
        return "Pawn";
    }
}
