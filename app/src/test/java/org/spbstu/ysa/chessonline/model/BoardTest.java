package org.spbstu.ysa.chessonline.model;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.pieces.Bishop;
import org.spbstu.ysa.chessonline.model.pieces.King;
import org.spbstu.ysa.chessonline.model.pieces.Knight;
import org.spbstu.ysa.chessonline.model.pieces.Pawn;
import org.spbstu.ysa.chessonline.model.pieces.Queen;
import org.spbstu.ysa.chessonline.model.pieces.Rook;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {

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
    void makeMoveTests() throws CloneNotSupportedException {
        //Common moves
        Cell[][] testBoardData = createEmptyBoard();
        Cell testingCell = testBoardData[1][4];
        testingCell.setPiece(new Knight(true));

        Board testBoard = new Board(testBoardData);

        assertNull(testBoard.getData()[3][5].getPiece());
        testBoard.capturePiece(testBoard.getData()[1][4]);

        Set<Cell> testRes = new HashSet<>();
        assertNull(testBoard.putPiece(testBoard.getData()[2][4]));

        testRes.addAll(testBoard.putPiece(testBoard.getData()[3][5]));
        assertFalse(testRes.isEmpty());
        assertEquals(new Knight(true), testBoard.getData()[3][5].getPiece());
        //  _________________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|_>_|_x_|___|___|
        // 2|___|___|___|___|_^_|___|___|___|
        // 1|___|___|___|___|KnW|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7


        //Pawns moves on 2 cells and passant realisation
        Cell[][] testBoardData2 = createEmptyBoard();
        testBoardData2[2][3].setPiece(new Pawn(true));
        testBoardData2[6][2].setPiece(new Pawn(false));

        Board testBoard2 = new Board(testBoardData2);
        //   _Creating__board_______________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|PaB|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|PaW|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        assertNull(testBoard2.getData()[4][3].getPiece());
        Pawn testingPawn = (Pawn) testBoard2.getData()[2][3].getPiece();
        testBoard2.capturePiece(testBoard2.getData()[2][3]);

        Set<Cell> testRes2 = new HashSet<>();
        testRes2.addAll(testBoard2.putPiece(testBoard2.getData()[4][3]));
        assertFalse(testRes2.isEmpty());
        assertTrue(testingPawn.isPassantAvailable);
        //   _______________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|PaB|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|PaW|___|___|___|___|
        // 3|___|___|___|_^_|___|___|___|___|
        // 2|___|___|___|_^_|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        testBoard2.capturePiece(testBoard2.getData()[6][2]);
        testBoard2.putPiece(testBoard2.getData()[4][2]);
        assertFalse(testingPawn.isPassantAvailable);
        //   _______________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|_v_|___|___|___|___|___|
        // 5|___|___|_v_|___|___|___|___|___|
        // 4|___|___|PaB|PaW|___|___|___|___| now PaW can passant PaB
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        testBoard2.capturePiece(testBoard2.getData()[4][3]);
        Set<Cell> expResOfPassant = new HashSet<>();
        expResOfPassant.addAll(testBoard2.putPiece(testBoard2.getData()[5][2]));
        assertTrue(expResOfPassant.contains(new Cell(2, 4)));

        //promotion realisation
        Cell[][] testBoardData3 = createEmptyBoard();
        testBoardData3[5][5].setPiece(new Pawn(true));

        Board testBoard3 = new Board(testBoardData3);
        assertNull(testBoard3.getPromotedCell());//promotion unable
        testBoard3.capturePiece(testBoard3.getData()[5][5]);

        testBoard3.putPiece(testBoard3.getData()[7][5]);
        assertNotNull(testBoard3.getPromotedCell());
        //   _Creating__board_______________
        // 7|___|___|___|___|___|_^_|___|___| now promotedCell != null -> start promotion
        // 6|___|___|___|___|___|_^_|___|___|
        // 5|___|___|___|___|___|PaW|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|___|___|___|___|___|___|___|___|
        //    0   1   2   3   4   5   6   7

        //Promotion
        testBoard3.makePromotion(new Queen(true));
        assertEquals(new Queen(true), testBoard3.getData()[7][5].getPiece());


        //Castling

        Cell[][] testBoardData4 = createEmptyBoard();
        testBoardData4[0][4].setPiece(new King(true));
        testBoardData4[0][0].setPiece(new Rook(true));
        testBoardData4[0][7].setPiece(new Rook(true));

        Board templateForTest = new Board(testBoardData4);
        //short castling
        Board testBoard4 = templateForTest.clone();
        //   _Creating__board_______________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|RoW|___|___|___|KGW|___|___|RoW|
        //    0   1   2   3   4   5   6   7
        assertNull(testBoard4.getData()[0][5].getPiece());
        assertNull(testBoard4.getData()[0][6].getPiece());
        testBoard4.capturePiece(testBoard4.getData()[0][4]);
        testBoard4.putPiece(testBoard4.getData()[0][7]);//short castling
        assertEquals(new Rook(true), testBoard4.getData()[0][5].getPiece());
        assertEquals(new King(true), testBoard4.getData()[0][6].getPiece());
        //   _______________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|_v-|<-<|<-_|
        // 0|RoW|___|___|___|KGW|>->|___|RoW|
        //    0   1   2   3   4   5   6   7

        //long castling
        Board testBoard5 = templateForTest.clone();
        //   _Creating__board_______________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|___|___|___|___|___|___|___|___|
        // 0|RoW|___|___|___|KGW|___|___|RoW|
        //    0   1   2   3   4   5   6   7
        assertNull(testBoard5.getData()[0][2].getPiece());
        assertNull(testBoard5.getData()[0][3].getPiece());
        testBoard5.capturePiece(testBoard5.getData()[0][4]);
        testBoard5.putPiece(testBoard5.getData()[0][0]);
        assertEquals(new Rook(true), testBoard5.getData()[0][3].getPiece());
        assertEquals(new King(true), testBoard5.getData()[0][2].getPiece());
        //   _______________________________
        // 7|___|___|___|___|___|___|___|___|
        // 6|___|___|___|___|___|___|___|___|
        // 5|___|___|___|___|___|___|___|___|
        // 4|___|___|___|___|___|___|___|___|
        // 3|___|___|___|___|___|___|___|___|
        // 2|___|___|___|___|___|___|___|___|
        // 1|_->|>->|>->|-v_|___|___|___|___|
        // 0|RoW|___|_x_|<-<|KGW|___|___|RoW|
        //    0   1   2   3   4   5   6   7
    }

    @Test
    void checkAndCheckmateTests() {

        Cell[][] testBoardData = createEmptyBoard();
        testBoardData[0][4].setPiece(new King(true));
        testBoardData[1][7].setPiece(new Rook(false));
        testBoardData[5][2].setPiece(new Bishop(false));

        Board testBoard = new Board(testBoardData);

        assertFalse(testBoard.isCheck(true));

        testBoard.getData()[7][4].setPiece(new Rook(false));

        assertTrue(testBoard.isCheck(true));
        assertFalse(testBoard.isCheckmate(true));
        //  _________________________________
        // 7|___|___|___|___|RoB|___|___|___|
        // 6|___|___|___|___|_v_|___|___|___|
        // 5|___|___|BiB|___|_v_|___|___|___|
        // 4|___|___|___|___|_v_|___|___|___|
        // 3|___|___|___|___|_v_|___|___|___|
        // 2|___|___|___|___|_v_|___|___|___|
        // 1|___|___|___|___|_v_|___|___|RoB|
        // 0|___|___|___|___|KGW|___|___|___|
        //    0   1   2   3   4   5   6   7

        testBoard.getData()[7][4].removePiece();
        testBoard.getData()[0][0].setPiece(new Rook(false));

        assertTrue(testBoard.isCheckmate(true));
    }


    @Test
    void testClone() throws CloneNotSupportedException {
        Board testingBoard = new Board();
        Board copyBoard = testingBoard.clone();
        copyBoard.getData()[0][0].removePiece();
        assertEquals(new Rook(true),testingBoard.getData()[0][0].getPiece());
    }

}