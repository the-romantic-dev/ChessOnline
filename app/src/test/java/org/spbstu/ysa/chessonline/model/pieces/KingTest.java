package org.spbstu.ysa.chessonline.model.pieces;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Board;
import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KingTest {

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
        testingCell1.setPiece(new King(true));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|_x_|_x_|_x_|___|___|___|
        // 3|___|___|_x_|_K_|_x_|___|___|___|
        // 2|___|___|_x_|_x_|_x_|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        for (int i = 2; i < 5; i++){
            expTest1.add(new Cell(2,i));
            expTest1.add(new Cell(4,i));
        }
        expTest1.add(new Cell(3,2));
        expTest1.add(new Cell(3,4));
        assertEquals(expTest1, testingCell1.getPiece().getAllowedCells(testingCell1,new Board(testBoard1)));

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[3][3];
        testingCell2.setPiece(new King(true));
        testBoard2[3][2].setPiece(new Pawn(true,2,3));
        testBoard2[3][4].setPiece(new Pawn(true,4,3));
        testBoard2[4][3].setPiece(new Pawn(false,3,4));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|_x_|PaB|_x_|___|___|___|
        // 3|___|___|PaW|KgW|PaW|___|___|___|
        // 2|___|___|_x_|_x_|_x_|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest2 = new HashSet<>();
        for (int i = 2; i < 5; i++){
            expTest2.add(new Cell(i,2));
            expTest2.add(new Cell(i,4));
        }
        assertEquals(expTest2, testingCell2.getPiece().getAllowedCells(testingCell2,new Board(testBoard2)));
    }

    @Test
    void filterAllowedMoves() {
        Cell[][] testBoard1 = createEmptyBoard();
        Cell testingCell1 = testBoard1[3][3];
        testingCell1.setPiece(new King(true));
        testBoard1[3][3].setPiece(new King(true));
        testBoard1[4][4].setPiece(new Queen(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|QuB|___|___|___|
        // 3|___|___|_x_|KgW|___|___|___|___|
        // 2|___|___|___|_x_|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|_x_|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest1 = new HashSet<>();
        expTest1.add(new Cell(3,2));
        expTest1.add(new Cell(2,3));
        expTest1.add(new Cell(4,4));
        try {
            assertEquals(expTest1, testingCell1.getPiece().filterAllowedMoves(testingCell1,new Board(testBoard1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard2 = createEmptyBoard();
        Cell testingCell2 = testBoard2[0][0];
        testingCell2.setPiece(new King(true));
        testBoard2[7][0].setPiece(new Rook(false));
        testBoard2[5][1].setPiece(new Rook(false));
        //  _________________________________
        // 7|RoB|___|___|___|___|___|___|___|
        // 6|___|RoB|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
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
        Cell testingCell3 = testBoard3[0][4];
        testingCell3.setPiece(new King(true));
        testBoard3[0][0].setPiece(new Rook(true));
        testBoard3[0][7].setPiece(new Rook(true));
        testBoard3[1][3].setPiece(new Pawn(true,3,1));
        testBoard3[1][4].setPiece(new Pawn(true,4,1));
        testBoard3[1][5].setPiece(new Pawn(true,5,1));

        Cell[][] copy1 = testBoard3.clone();
        Cell[][] copy2 = testBoard3.clone();
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|_P_|_P_|_P_|___|___|
        // 0|_R_|___|___|_x_|_K_|_x_|___|_R_|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest3 = new HashSet<>();
        expTest3.add(new Cell(0,0));
        expTest3.add(new Cell(7,0));
        expTest3.add(new Cell(3,0));
        expTest3.add(new Cell(5,0));

        try {
            assertEquals(expTest3, testingCell3.getPiece().filterAllowedMoves(testingCell3,new Board(testBoard3)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Cell[][] testBoard4 = createEmptyBoard();
        Cell testingCell4 = testBoard4[0][4];
        testingCell4.setPiece(new King(true));
        testBoard4[0][0].setPiece(new Rook(true));
        testBoard4[0][7].setPiece(new Rook(true));
        testBoard4[1][3].setPiece(new Pawn(true,3,1));
        testBoard4[1][4].setPiece(new Pawn(true,4,1));
        testBoard4[7][5].setPiece(new Rook(false));
        //  _________________________________
        // 7|___|___|___|___|___|RoB|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|PaW|PaW|___|___|___|
        // 0|RoW|___|___|_x_|KgW|___|___|Row|
        //    0   1   2   3   4   5   6   7

        Set<Cell> expTest4 = new HashSet<>();
        expTest4.add(new Cell(0,0));
        expTest4.add(new Cell(3,0));

        try {
            assertEquals(expTest4, testingCell4.getPiece().filterAllowedMoves(testingCell4,new Board(testBoard4)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        testBoard4[7][5].removePiece();
        testBoard4[7][6].setPiece(new Rook(false));
        //  _________________________________
        // 7|___|___|___|___|___|___|RoB|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|PaW|PaW|_x_|___|___|
        // 0|RoW|___|___|_x_|KgW|_x_|___|Row|
        //    0   1   2   3   4   5   6   7
        expTest4.add(new Cell(5,0));
        expTest4.add(new Cell(5,1));

        try {
            assertEquals(expTest4, testingCell4.getPiece().filterAllowedMoves(testingCell4,new Board(testBoard4)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Rook rook = (Rook) copy1[0][0].getPiece();
        rook.setMoved();
        expTest3.remove(copy1[0][0]);

        try {
            assertEquals(expTest3, testingCell3.getPiece().filterAllowedMoves(testingCell3,new Board(copy1)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        King king = (King) copy2[0][4].getPiece();
        king.setMoved();
        expTest3.remove(copy1[0][7]);

        try {
            assertEquals(expTest3, testingCell3.getPiece().filterAllowedMoves(testingCell3,new Board(copy2)));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testClone() {
        Cell[][] testBoard = createEmptyBoard();
        King testingKing = new King(true);
        testBoard[0][0].setPiece(testingKing);
        try {
            Piece copyKing = testBoard[0][0].getPiece().clone();
            copyKing = null;

            assertEquals(testingKing, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
