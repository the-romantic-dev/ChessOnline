package org.spbstu.ysa.chessonline.model.pieces;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RookTest {

    private Cell[][] createEmptyBoard() {
        Cell[][] board = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(j, i);
            }
        }
        return board;
    }

    @Test
    void getAllowedCells() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[3][3];
        testingCell1.setPiece(new Rook(true));
        //  _________________________________
        // 7|___|___|___|_x_|___|___|___|___|
        // 6|___|___|___|_x_|___|___|___|___|
        // 5|___|___|___|_x_|___|___|___|___|
        // 4|___|___|___|_x_|___|___|___|___|
        // 3|_x_|_x_|_x_|_R_|_x_|_x_|_x_|_x_|
        // 2|___|___|___|_x_|___|___|___|___|
        // 1|___|___|___|_x_|___|___|___|___|
        // 0|___|___|___|_x_|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            if (i == 3) continue;
            expTest1.add(new Cell(i, 3));
            expTest1.add(new Cell(3, i));
        }
        assertEquals(expTest1, testingCell1.getPiece().getAllowedCells(testingCell1, new Board(testBoard1)));

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard1[3][3];
        testingCell2.setPiece(new Rook(true));
        testBoard2[5][3].setPiece(new Pawn(true));
        testBoard2[3][4].setPiece(new Pawn(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|PaW|___|___|___|___|
        // 4|___|___|___|_x_|___|___|___|___|
        // 3|_x_|_x_|_x_|RoW|PaB|___|___|___|
        // 2|___|___|___|_x_|___|___|___|___|
        // 1|___|___|___|_x_|___|___|___|___|
        // 0|___|___|___|_x_|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            if (i == 3) continue;
            expTest2.add(new Cell(i, 3));
            expTest2.add(new Cell(3, i));
        }
        assertEquals(expTest2, testingCell2.getPiece().getAllowedCells(testingCell2, new Board(testBoard2)));
    }

    @Test
    void filterAllowedMoves() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[2][3];
        testingCell1.setPiece(new Rook(true));
        testBoard1[0][3].setPiece(new King(true));
        testBoard1[7][3].setPiece(new Queen(false));
        //  _________________________________
        // 7|___|___|___|QuB|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|RoW|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|KGW|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        for (int i = 1; i < 8; i++) {
            if (i == 2) continue;
            expTest1.add(new Cell(3, i));
        }
        try {
            assertEquals(expTest1, testingCell1.getPiece().filterAllowedMoves(testingCell1, new Board(testBoard1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[3][3];
        testingCell2.setPiece(new Rook(true));
        testBoard2[0][0].setPiece(new King(true));
        testBoard2[2][2].setPiece(new Queen(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|RoW|___|___|___|___|
        // 2|___|___|QuB|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();

        try {
            assertEquals(expTest2, testingCell2.getPiece().filterAllowedMoves(testingCell2, new Board(testBoard2)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard3 = createEmptyBoard();
        Cell testingCell3 = testBoard3[3][3];
        testingCell3.setPiece(new Rook(true));
        testBoard3[0][0].setPiece(new King(true));
        testBoard3[7][7].setPiece(new Knight(false));
        testBoard3[5][5].setPiece(new Pawn(true));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|BiB|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|PaW|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|KnW|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        try {
            assertEquals(testingCell3.getPiece().getAllowedCells(testingCell3, new Board(testBoard3))
                    , testingCell3.getPiece().filterAllowedMoves(testingCell3, new Board(testBoard3)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testClone() {
        Cell[][] testBoard = createEmptyBoard();
        Rook testingRook = new Rook(true);
        testBoard[0][0].setPiece(testingRook);
        try {
            Piece copyRook = testBoard[0][0].getPiece().clone();
            copyRook = null;

            assertEquals(testingRook, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }


}