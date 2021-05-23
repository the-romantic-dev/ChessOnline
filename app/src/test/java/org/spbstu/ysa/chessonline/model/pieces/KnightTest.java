package org.spbstu.ysa.chessonline.model.pieces;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

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
        testingCell1.setPiece(new Knight(true));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|_x_|___|_x_|___|___|___|
        // 4|___|_x_|___|___|___|_x_|___|___|
        // 3|___|___|___|_K_|___|___|___|___|
        // 2|___|_x_|___|___|___|_x_|___|___|
        // 1|___|___|_x_|___|_x_|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        expTest1.add(new Cell(1,2));
        expTest1.add(new Cell(1,4));
        expTest1.add(new Cell(2,1));
        expTest1.add(new Cell(2,5));
        expTest1.add(new Cell(4,5));
        expTest1.add(new Cell(4,1));
        expTest1.add(new Cell(5,2));
        expTest1.add(new Cell(5,4));
        assertEquals(expTest1, testingCell1.getPiece().getAllowedCells(testingCell1,new Board(testBoard1)));

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard1[3][3];
        testingCell2.setPiece(new Knight(true));
        testBoard2[5][2].setPiece(new Pawn(true ,2,5));
        testBoard2[1][4].setPiece(new Pawn(false ,4,1));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|PaW|___|_x_|___|___|___|
        // 4|___|_x_|___|___|___|_x_|___|___|
        // 3|___|___|___|KnW|___|___|___|___|
        // 2|___|_x_|___|___|___|_x_|___|___|
        // 1|___|___|_x_|___|PaB|___|___|___|
        // 0|_x_|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        expTest1.remove(new Cell(2,4));
        assertEquals(expTest1, testingCell2.getPiece().getAllowedCells(testingCell2,new Board(testBoard2)));
    }

    @Test
    void filterAllowedMoves() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[2][3];
        testingCell1.setPiece(new Knight(true));
        testBoard1[0][0].setPiece(new King(true));
        testBoard1[4][4].setPiece(new Knight(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|BiB|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|KnW|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        expTest1.add(new Cell(4,4));
        try {
            assertEquals(expTest1, testingCell1.getPiece().filterAllowedMoves(testingCell1,new Board(testBoard1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[3][3];
        testingCell2.setPiece(new Knight(true));
        testBoard2[0][0].setPiece(new King(true));
        testBoard2[7][0].setPiece(new Rook(false));
        //  _________________________________
        // 7|RoB|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|KnW|___|___|___|___|
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
        testingCell3.setPiece(new Knight(true));
        testBoard3[0][0].setPiece(new King(true));
        testBoard3[7][7].setPiece(new Knight(false));
        testBoard3[5][5].setPiece(new Pawn(true,5,5));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|BiB|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|_x_|___|_x_|PaW|___|___|
        // 4|___|_x_|___|___|___|_x_|___|___|
        // 3|___|___|___|KnW|___|___|___|___|
        // 2|___|_x_|___|___|___|_x_|___|___|
        // 1|___|___|_x_|___|_x_|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
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
        Knight testingKnight = new Knight(true);
        testBoard[0][0].setPiece(testingKnight);
        try {
            Piece copyKnight = testBoard[0][0].getPiece().clone();
            copyKnight = null;

            assertEquals(testingKnight, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}