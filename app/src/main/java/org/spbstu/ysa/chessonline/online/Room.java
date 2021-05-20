package org.spbstu.ysa.chessonline.online;

public class Room {
    private String password;
    private Boolean connection;
    private Boolean creatorIsWhite;
    private String pawnTo;
    private int xFrom;
    private int yFrom;
    private int xTo;
    private int yTo;

    public Room() {
    }

    public Room(String password, boolean connection, boolean isWhite, String pawnTo, int xFrom, int yFrom, int xTo, int yTo) {
        this.password = password;
        this.connection = connection;
        this.creatorIsWhite = isWhite;
        this.pawnTo = pawnTo;
        this.xFrom = xFrom;
        this.yFrom = yFrom;
        this.xTo = xTo;
        this.yTo = yTo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean getConnection() { return connection; }

    public void setConnection(boolean connection) { this.connection = connection; }

    public Boolean getCreatorColor() {
        return creatorIsWhite;
    }

    public void setCreatorColor(Boolean white) {
        creatorIsWhite = white;
    }
}
