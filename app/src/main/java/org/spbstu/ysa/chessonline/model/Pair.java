package org.spbstu.ysa.chessonline.model;

public class Pair<X, Y> {
    private X x;
    private Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public void setX(X x) {
        this.x = x;
    }

    public void setY(Y y) {
        this.y = y;
    }

    public static <X, Y> Pair<X, Y> pairOf(X x, Y y) {
        return new Pair<>(x, y);
    }

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return this.x.equals(pair.getX()) &&
                this.y.equals(pair.getY());
    }

    @Override
    public String toString() {
        return "{ x=" + x +
                ", y=" + y +
                '}';
    }
}
