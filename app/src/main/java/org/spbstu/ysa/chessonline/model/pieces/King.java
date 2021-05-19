package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class King extends Piece {

    public King(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Board boardClass) {
        Set<Cell> res = new HashSet<>();

        Cell[][] board = boardClass.getData();

        List<Integer> listOfIterator = new ArrayList<>();
        listOfIterator.add(-1);
        listOfIterator.add(0);
        listOfIterator.add(1);

        for (int iteratorX : listOfIterator) {
            for (int iteratorY : listOfIterator) {
                if (iteratorX == 0 && iteratorY == 0) continue;
                int curX = x + iteratorX;
                int curY = y + iteratorY;
                if (curX >= 0 && curX <= 7 && curY >= 0 && curY <= 7
                        && (board[curY][curX].getPiece() == null
                        || board[curY][curX].getPiece().isWhite() != this.isWhite()))
                    res.add(new Cell(curX, curY));
            }
        }
        return res;
    }

    @Override
    public String getName() {
        return "King";
    }
}
