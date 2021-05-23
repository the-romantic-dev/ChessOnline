package org.spbstu.ysa.chessonline.model.pieces;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.Cell;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

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
    void testClone() {
        Cell[][] testBoard = createEmptyBoard();
        Queen testingQueen = new Queen(true);
        testBoard[0][0].setPiece(testingQueen);
        try {
            Piece copyQueen = testBoard[0][0].getPiece().clone();
            copyQueen = null;

            assertEquals(testingQueen, testBoard[0][0].getPiece());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}