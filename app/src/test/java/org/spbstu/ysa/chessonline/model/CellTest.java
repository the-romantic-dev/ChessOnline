package org.spbstu.ysa.chessonline.model;

import org.junit.jupiter.api.Test;
import org.spbstu.ysa.chessonline.model.pieces.Bishop;
import org.spbstu.ysa.chessonline.model.pieces.King;
import org.spbstu.ysa.chessonline.model.pieces.Knight;
import org.spbstu.ysa.chessonline.model.pieces.Pawn;
import org.spbstu.ysa.chessonline.model.pieces.Queen;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void constructorAndEqualsTest() {
        assertEquals(new Cell(0, 0, null), new Cell(0, 0));
    }

    @Test
    void getterTests() {
        Cell testingCell = new Cell(0, 0);
        assertEquals(0, testingCell.getX());
        assertEquals(0, testingCell.getY());

        Cell testingCell2 = new Cell(2, 8);
        assertEquals(2, testingCell2.getX());
        assertEquals(8, testingCell2.getY());

        assertNull(testingCell.getPiece());
        assertNull(testingCell2.getPiece());

        Cell testingCell3 = new Cell(0, 0, new Pawn(true));
        assertEquals(new Pawn(true), testingCell3.getPiece());
    }


    @Test
    void setPiece() {
        Cell testCell = new Cell(0, 0);
        testCell.setPiece(new Queen(false));
        assertEquals(new Queen(false), testCell.getPiece());
    }

    @Test
    void removePiece() {
        Cell testCell = new Cell(0, 0, new Queen(false));
        testCell.removePiece();
        assertNull(testCell.getPiece());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Cell testingCell = new Cell(1,1, new King(true));
        Cell copyCell = testingCell.clone();
        copyCell.removePiece();
        assertEquals(new King(true), testingCell.getPiece());
    }

    @Test
    void testToString() {
        Cell testingCell = new Cell(1,5, new Bishop(false));
        assertEquals("Cell{coordinates=(1 ; 5) piece=black_Bishop}", testingCell.toString());
    }

    @Test
    void testHashCode() {
        Cell testingCell = new Cell(5,5,new Knight(true));
        Cell testingCell2 = new Cell(5,5,new Knight(true));
        Cell testingCell3 = new Cell(5,5,new Knight(false));
        Cell testingCell4 = new Cell(1,5,new Knight(true));
        Cell testingCell5 = new Cell(5,3,new Knight(true));

        assertEquals(testingCell.hashCode(),testingCell2.hashCode());
        assertNotEquals(testingCell.hashCode(),testingCell3.hashCode());
        assertNotEquals(testingCell.hashCode(),testingCell4.hashCode());
        assertNotEquals(testingCell.hashCode(),testingCell5.hashCode());
    }
}