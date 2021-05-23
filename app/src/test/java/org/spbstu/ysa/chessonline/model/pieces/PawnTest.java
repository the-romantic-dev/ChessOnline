package org.spbstu.ysa.chessonline.model.pieces;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

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
        Cell testingWhiteCell1 = testBoard1[1][5];
        Cell testingBlackCell1 = testBoard1[6][4];

        testingWhiteCell1.setPiece(new Pawn(true));
        testingBlackCell1.setPiece(new Pawn(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|PaB|___|___|___|
        // 5|___|___|___|___|_x_|___|___|___|
        // 4|___|___|___|___|_x_|___|___|___|
        // 3|___|___|___|___|___|_x_|___|___|
        // 2|___|___|___|___|___|_x_|___|___|
        // 1|___|___|___|___|___|PaW|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTestWhite1 = new HashSet<>();
        Set<Cell> expTestBlack1 = new HashSet<>();
        expTestWhite1.add(new Cell(5,2));
        expTestWhite1.add(new Cell(5,3));
        expTestBlack1.add(new Cell(4,4));
        expTestBlack1.add(new Cell(4,5));

        assertEquals(expTestWhite1, testingWhiteCell1.getPiece().getAllowedCells(testingWhiteCell1,new Board(testBoard1)));
        assertEquals(expTestBlack1, testingBlackCell1.getPiece().getAllowedCells(testingBlackCell1,new Board(testBoard1)));

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingWhiteCell2 = testBoard1[2][5];
        Cell testingBlackCell2 = testBoard1[5][4];

        testingWhiteCell2.setPiece(new Pawn(true));
        testingBlackCell2.setPiece(new Pawn(false));
        Pawn blackPawn = (Pawn) testingBlackCell2.getPiece();
        blackPawn.setMoved();
        Pawn whitePawn = (Pawn) testingWhiteCell2.getPiece();
        whitePawn.setMoved();
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|PaB|___|___|___|
        // 4|___|___|___|___|_x_|___|___|___|
        // 3|___|___|___|___|___|_x_|___|___|
        // 2|___|___|___|___|___|PaW|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTestWhite2 = new HashSet<>();
        Set<Cell> expTestBlack2 = new HashSet<>();
        expTestBlack2.add(new Cell(4,4));
        expTestWhite2.add(new Cell(5,3));

        assertEquals(expTestWhite2, testingWhiteCell2.getPiece().getAllowedCells(testingWhiteCell2,new Board(testBoard2)));
        assertEquals(expTestBlack2, testingBlackCell2.getPiece().getAllowedCells(testingBlackCell2,new Board(testBoard2)));

        Cell[][] testBoard3 = createEmptyBoard();
        Cell testingWhiteCell3 = testBoard1[1][5];
        Cell testingBlackCell3 = testBoard1[6][4];

        testingWhiteCell1.setPiece(new Pawn(true));
        testingBlackCell1.setPiece(new Pawn(false));
        for (int x = 3;x < 6; x++){
            boolean isWhite = true;
            if (x == 4) isWhite = false;
            testBoard3[5][x].setPiece(new Bishop(isWhite));
            testBoard3[2][x + 1].setPiece(new Bishop(!isWhite));
        }
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|PaB|___|___|___|
        // 5|___|___|___|BiW|BiB|BiW|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|BiB|BiW|BiB|___|
        // 1|___|___|___|___|___|PaW|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTestWhite3 = new HashSet<>();
        Set<Cell> expTestBlack3 = new HashSet<>();
        expTestWhite3.add(new Cell(4,2));
        expTestWhite3.add(new Cell(6,2));
        expTestBlack3.add(new Cell(3,5));
        expTestBlack3.add(new Cell(5,5));

        assertEquals(expTestWhite3, testingWhiteCell3.getPiece().getAllowedCells(testingWhiteCell3,new Board(testBoard3)));
        assertEquals(expTestBlack3, testingBlackCell3.getPiece().getAllowedCells(testingBlackCell3,new Board(testBoard3)));

        Cell[][] testBoard4 = createEmptyBoard();
        Cell testingWhiteCell4 = testBoard4[4][5];
        testingWhiteCell4.setPiece(new Pawn(true));
        Pawn white = (Pawn) testingWhiteCell4.getPiece();
        white.setMoved();

        testBoard4[4][4].setPiece(new Pawn(false));
        Pawn blackPawn2 = (Pawn) testBoard4[4][4].getPiece();
        blackPawn2.isPassantAvailable = true;

        Cell testingBlackCell4 = testBoard4[3][2];
        testingBlackCell4.setPiece(new Pawn(false));
        Pawn black = (Pawn) testingBlackCell4.getPiece();
        black.setMoved();

        testBoard4[3][1].setPiece(new Pawn(true));
        Pawn whitePawn2 = (Pawn) testBoard4[3][1].getPiece();
        whitePawn2.isPassantAvailable = true;

        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|_v_|___|___|___|
        // 5|___|___|___|___|_v_|___|___|___|
        // 4|___|___|___|___|PaB|PaW|___|___|
        // 3|___|PaW|PaB|___|___|___|___|___|
        // 2|___|_^_|___|___|___|___|___|___|
        // 1|___|_^_|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTestWhite4 = new HashSet<>();
        expTestWhite4.add(new Cell(5,5));
        expTestWhite4.add(new Cell(4,5));

        assertEquals(expTestWhite4, testingWhiteCell4.getPiece().getAllowedCells(testingWhiteCell4,new Board(testBoard4)));



        Set<Cell> expTestBlack4 = new HashSet<>();
        expTestBlack4.add(new Cell(2,2));
        expTestBlack4.add(new Cell(1,2));
        assertEquals(expTestBlack4, testingBlackCell4.getPiece().getAllowedCells(testingBlackCell4,new Board(testBoard4)));

    }

    @Test
    void filterAllowedMoves() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[1][5];
        testingCell1.setPiece(new Pawn(true));
        testBoard1[0][4].setPiece(new King(true));
        testBoard1[2][4].setPiece(new Rook(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|RoB|___|___|___|
        // 1|___|___|___|___|___|PaW|___|___|
        // 0|___|___|___|___|KGW|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        expTest1.add(new Cell(4,2));
        try {
            assertEquals(expTest1, testingCell1.getPiece().filterAllowedMoves(testingCell1,new Board(testBoard1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[1][1];
        testingCell2.setPiece(new Pawn(true));
        testBoard2[0][0].setPiece(new King(true));
        testBoard2[7][0].setPiece(new Rook(false));
        //  _________________________________
        // 7|RoB|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|PaW|___|___|___|___|___|___|
        // 0|KGW|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();

        try {
            assertEquals(expTest2, testingCell2.getPiece().filterAllowedMoves(testingCell2,new Board(testBoard2)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard3 = createEmptyBoard();
        Cell testingCell3 = testBoard3[1][1];
        testingCell3.setPiece(new Pawn(true));
        testBoard3[0][0].setPiece(new King(true));
        testBoard3[7][7].setPiece(new Bishop(false));
        testBoard3[5][5].setPiece(new Pawn(true));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|BiB|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|PaW|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|_x_|___|___|___|___|___|___|
        // 2|___|_x_|___|___|___|___|___|___|
        // 1|___|PaW|___|___|___|___|___|___|
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
        Pawn testingPawn = new Pawn(true);
        testBoard[0][0].setPiece(testingPawn);
        try {
            Piece copyPawn = testBoard[0][0].getPiece().clone();
            copyPawn = null;

            assertEquals(testingPawn, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}