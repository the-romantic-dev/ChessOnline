package org.spbstu.ysa.chessonline.model.pieces;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RookTest extends TestCase {

    public void testGetAllowedCells() {
        Cell[][] board = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(i, j);
            }
        }

        board[2][0] = new Cell (6 , 0 , new Rook(true));
        board[6][0] = new Cell (6 , 0 , new Rook(true));
        board[2][3] = new Cell (2 , 3 , new Bishop(false));

        Set<Cell> set = new HashSet();
        set.add(new Cell(0 , 0));
        set.add(new Cell(1 , 0));
        set.add(new Cell(4, 0));
        set.add(new Cell(5 , 0));
        set.add(new Cell(2 , 2));
        set.add(new Cell(2 , 3));
        set.add(new Cell(3 , 0));
        set.add(new Cell(2 , 1));
        assertEquals(board[2][0].getPiece().getAllowedCells(2,0, board), set);
    }

}