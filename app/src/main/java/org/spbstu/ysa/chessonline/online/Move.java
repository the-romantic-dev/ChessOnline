package org.spbstu.ysa.chessonline.online;

public class Move {
    private String pawnTo;
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;

    public Move() {
    }

    public Move(String pawnTo, int xFrom, int yFrom, int xTo, int yTo) {
        this.pawnTo = pawnTo;
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
    }

    public String getPawnTo() {
        return pawnTo;
    }

    public void setPawnTo(String pawnTo) {
        this.pawnTo = pawnTo;
    }

    public int getxFrom() {
        return xFrom;
    }

    public void setxFrom(int xFrom) {
        this.xFrom = xFrom;
    }

    public int getyFrom() {
        return yFrom;
    }

    public void setyFrom(int yFrom) {
        this.yFrom = yFrom;
    }

    public int getxTo() {
        return xTo;
    }

    public void setxTo(int xTo) {
        this.xTo = xTo;
    }

    public int getyTo() {
        return yTo;
    }

    public void setyTo(int yTo) {
        this.yTo = yTo;
    }
}
