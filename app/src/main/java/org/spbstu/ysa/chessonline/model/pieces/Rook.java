package org.spbstu.ysa.chessonline.model.pieces;

import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Pair;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(boolean color) {
        super(color);
    }

    @Override
    public Set<Pair<Integer, Integer>> allowedMove(int x, int y) {
        Set<Pair<Integer, Integer>> res = new HashSet();

        if (x < 7) {
            for (int i = x + 1; i < 8; i++) {
                Piece curPiece = Board.getData()[i][y].takePiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(Pair.pairOf(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }
        if (x > 0) {
            for (int i = x - 1; i >= 0 ; i--) {
                Piece curPiece = Board.getData()[i][y].takePiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(Pair.pairOf(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }

        if (y < 7) {
            for (int i = y + 1; i < 8; i++) {
                Piece curPiece = Board.getData()[x][i].takePiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(Pair.pairOf(x, i));
                if (curPiece == null) continue;
                else break;
            }
        }
        if (y > 0) {
            for (int i = y - 1; i >= 0 ; i--) {
                Piece curPiece = Board.getData()[i][y].takePiece();
                if (curPiece.getColor() == this.getColor()) break;
                res.add(Pair.pairOf(i, y));
                if (curPiece == null) continue;
                else break;
            }
        }

        return res;
    }

    @Override
    public String getName() {
        return null;
    }
}
