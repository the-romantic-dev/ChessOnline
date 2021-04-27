package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color);
    }

    @Override
    public Set<Cell> getAllowedCells(int x, int y, Cell[][] board) {
        Set<Cell> res = new HashSet<>();

        if (x < 7) {
            for (int i = x + 1; i < 8; i++) {
                Piece curPiece = board[i][y].getPiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(new Cell(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }
        if (x > 0) {
            for (int i = x - 1; i >= 0 ; i--) {
                Piece curPiece = board[i][y].getPiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(new Cell(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }

        if (y < 7) {
            for (int i = y + 1; i < 8; i++) {
                Piece curPiece = board[x][i].getPiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(new Cell(x, i));
                if (curPiece == null) continue;
                else break;
            }
        }
        if (y > 0) {
            for (int i = y - 1; i >= 0 ; i--) {
                Piece curPiece = board[i][y].getPiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(new Cell(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }

        return res;
    }

    @Override
    public String getName() {
        return "Rook";
    }
}
