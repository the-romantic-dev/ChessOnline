package org.spbstu.ysa.chessonline.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import org.spbstu.ysa.chessonline.model.Cell;

import java.util.HashMap;
import java.util.Map;

public class ChessHelper {
    public static final int START_CORD_X = 35;
    public static final int START_CORD_Y = 100;
    public static final int cellSize = 128;
    public static Color borerColor = new Color(0.20f, 0.79f, 0.92f, 1);
    public static final int borderWidth = 5;

    public static final Color blackCellColor = new Color(0.66f, 0.42f, 0.07f, 1);
    public static final Color whiteCellColor = new Color(0.99f, 0.94f, 0.87f, 1);

    public static void drawPiece(SpriteBatch batch, int x, int y, FileHandle image) {
        Pixmap piece = new Pixmap(image);
        Texture texture = new Texture(piece);
        batch.draw(texture, x, y);
    }

    public static Map<Rectangle, Cell> getCellToCollider() {
        Map<Rectangle, Cell> result = new HashMap();
        int nextX = START_CORD_X;
        int nextY = START_CORD_Y - cellSize;
        for (int i = 0; i < 8; i++) {
            nextY += cellSize;
            nextX = START_CORD_X;
            for (int j = 0; j < 8; j++) {
                result.put(new Rectangle(nextX, nextY, cellSize, cellSize), new Cell(j, i));
                nextX += cellSize;
            }
        }
        return result;
    }

    public static void drawCell(SpriteBatch batch, int startX, int startY, Color color) {
        Pixmap cell = new Pixmap(cellSize, cellSize, Pixmap.Format.RGB888);
        cell.setColor(color);
        cell.fill();
        Texture texture = new Texture(cell);
        batch.draw(texture, startX, startY);
    }

    public static void drawBoard(SpriteBatch batch, Color white, Color black) {
        int nextX = START_CORD_X;
        int nextY = START_CORD_Y - cellSize;
        for (int i = 0; i < 8; i++) {
            nextY += cellSize;
            nextX = START_CORD_X;
            for (int j = 0; j < 8; j++) {
                drawCell(batch, nextX, nextY, (i + j) % 2 == 0 ? black : white);
                nextX += cellSize;
            }
        }
    }

    public static void drawBorder(SpriteBatch batch, Rectangle rect) {
        int x = (int) rect.x;
        int y = (int) rect.y;

        Pixmap border = new Pixmap(cellSize, cellSize, Pixmap.Format.RGBA8888);
        border.setColor(borerColor);
        border.fillRectangle(x, y, borderWidth, cellSize);
        border.fillRectangle(x, y + cellSize, cellSize, borderWidth);
        border.fillRectangle(x + cellSize, y + cellSize, cellSize, cellSize);
        border.fillRectangle(x + cellSize, y, borderWidth, cellSize);
    }
}
