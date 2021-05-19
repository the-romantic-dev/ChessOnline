package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    public Knight(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Board boardClass) {
        Set<Cell> res = new HashSet<>();

        Cell[][] board = boardClass.getData();

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int curX = (int) (x + i * Math.pow(-1.0, j));
                int curY = y + i - 3;
                if (coordinatesAllow(curX, curY, board)) res.add(new Cell(curX, curY));
                curY = y - i + 3;
                if (coordinatesAllow(curX, curY, board)) res.add(new Cell(curX, curY));
            }
        }

        return res;
    }

    @Override
    public String getName() {
        return "Knight";
    }


    private boolean coordinatesAllow(int x, int y, Cell[][] board) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7
                && (board[y][x].getPiece() == null
                || board[y][x].getPiece().isWhite() != this.isWhite());
    }
}
