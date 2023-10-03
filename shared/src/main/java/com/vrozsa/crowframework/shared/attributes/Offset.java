package com.vrozsa.crowframework.shared.attributes;

import java.util.Objects;

public class Offset {
    public static Offset origin() {
        return new Offset();
    }

    private int x;
    private int y;

    public Offset(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Offset() {
    }

    public void setOrigin() {
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Offset add(Offset target) {
        return new Offset(x + target.getX(), y + target.getY());
    }

    public Offset remove(Offset target) {
        return new Offset(x - target.getX(), y - target.getY());
    }

    @Override
    public String toString() {
        return String.format("%d,%d", x, y);
    }

    public Offset clone() {
        return new Offset(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offset offset = (Offset) o;
        return x == offset.x &&
                y == offset.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Offset updateOffset(Offset offset, Size originSize, Size targetSize) {
        int offsetX = (int)(((float)offset.getX() / originSize.getWidth()) * targetSize.getWidth());
        offsetX -= offset.getX();
        int offsetY = (int)(((float)offset.getY() / originSize.getHeight()) * targetSize.getHeight());
        offsetY  -= offset.getY();

        return new Offset(offsetX, offsetY);
    }
}
