package org.spbstu.ysa.chessonline.model.pieces;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    private Cell[][] createEmptyBoard(){
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
        testingCell1.setPiece(new Bishop(true));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|_x_|
        // 6|_x_|___|___|___|___|___|_x_|___|
        // 5|___|_x_|___|___|___|_x_|___|___|
        // 4|___|___|_x_|___|_x_|___|___|___|
        // 3|___|___|___|_B_|___|___|___|___|
        // 2|___|___|_x_|___|_x_|___|___|___|
        // 1|___|_x_|___|___|___|_x_|___|___|
        // 0|_x_|___|___|___|___|___|_x_|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        for (int i = 0; i < 7; i++){
            if (i ==3) continue;
            expTest1.add(new Cell(i,i));
            expTest1.add(new Cell(i,6 - i));
        }
        expTest1.add(new Cell(7,7));
        assertEquals(expTest1, testingCell1.getPiece().getAllowedCells(testingCell1,new Board(testBoard1)));

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard1[3][3];
        testingCell2.setPiece(new Bishop(true));
        testBoard2[4][2].setPiece(new Pawn(true ,2,4));
        testBoard2[2][4].setPiece(new Pawn(false ,4,2));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|_x_|
        // 6|___|___|___|___|___|___|_x_|___|
        // 5|___|___|___|___|___|_x_|___|___|
        // 4|___|___|PaW|___|_x_|___|___|___|
        // 3|___|___|___|BiW|___|___|___|___|
        // 2|___|___|_x_|___|PaB|___|___|___|
        // 1|___|_x_|___|___|___|___|___|___|
        // 0|_x_|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();
        for (int i = 0; i < 8; i++){
            if (i ==3) continue;
            expTest1.add(new Cell(i,i));
        }
        expTest1.add(new Cell(4,2));
        assertEquals(expTest1, testingCell2.getPiece().getAllowedCells(testingCell2,new Board(testBoard1)));
    }

    @Test
    void filterAllowedMoves() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[3][3];
        testingCell1.setPiece(new Bishop(true));
        testBoard1[0][0].setPiece(new King(true));
        testBoard1[7][7].setPiece(new Bishop(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|BiB|
        // 6|___|___|___|___|___|___|_x_|___|
        // 5|___|___|___|___|___|_x_|___|___|
        // 4|___|___|___|___|_x_|___|___|___|
        // 3|___|___|___|BiW|___|___|___|___|
        // 2|___|___|_x_|___|___|___|___|___|
        // 1|___|_x_|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        for (int i = 1; i < 8; i++){
            if (i ==3) continue;
            expTest1.add(new Cell(i,i));
        }
        try {
            assertEquals(expTest1, testingCell1.getPiece().filterAllowedMoves(testingCell1,new Board(testBoard1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[3][3];
        testingCell2.setPiece(new Bishop(true));
        testBoard2[0][0].setPiece(new King(true));
        testBoard2[7][7].setPiece(new Bishop(false));
        testBoard2[7][0].setPiece(new Rook(false));
        //  _________________________________
        // 7|RoB|___|___|___|___|___|___|BiB|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|BiW|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();

        try {
            assertEquals(expTest2, testingCell2.getPiece().filterAllowedMoves(testingCell2,new Board(testBoard2)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard3 = createEmptyBoard();
        Cell testingCell3 = testBoard3[3][3];
        testingCell3.setPiece(new Bishop(true));
        testBoard3[0][0].setPiece(new King(true));
        testBoard3[7][7].setPiece(new Bishop(false));
        testBoard3[5][5].setPiece(new Pawn(true,5,5));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|BiB|
        // 6|_x_|___|___|___|___|___|___|___|
        // 5|___|_x_|___|___|___|PaW|___|___|
        // 4|___|___|_x_|___|_x_|___|___|___|
        // 3|___|___|___|BiW|___|___|___|___|
        // 2|___|___|_x_|___|_x_|___|___|___|
        // 1|___|_x_|___|___|___|_x_|___|___|
        // 0|KGW|___|___|___|___|___|_x_|___|
        //    0   1   2   3   4   5   6   7

        try {
            assertEquals(testingCell3.getPiece().getAllowedCells(testingCell3,new Board(testBoard3))
                    , testingCell3.getPiece().filterAllowedMoves(testingCell3,new Board(testBoard3)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testClone() {
        Cell[][] testBoard = createEmptyBoard();
        Bishop testingBishop = new Bishop(true);
        testBoard[0][0].setPiece(testingBishop);
        try {
            Piece copyBishop = testBoard[0][0].getPiece().clone();
            copyBishop = null;

            assertEquals(testingBishop, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}