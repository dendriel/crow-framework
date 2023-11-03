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

    public static Offset of(final int x, final int y) {
        return new Offset(x, y);
    }

    /**
     * Creates a new offset using the same value for x and y.
     * @param dimension value to be set in x and y.
     * @return the created offset.
     */
    public static Offset of(final int dimension) {
        return new Offset(dimension, dimension);
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

    /**
     * Adds the target offset to this offset (idempotent). The base offset won't be changed.
     * @param target the offset to be added.
     * @return the new offset generated.
     */
    public Offset sum(Offset target) {
        return new Offset(x + target.getX(), y + target.getY());
    }

    /**
     * Removes the target offset to this offset (idempotent). The base offset won't be changed.
     * @param target the offset to be removed.
     * @return the new offset generated.
     */
    public Offset sub(Offset target) {
        return new Offset(x - target.getX(), y - target.getY());
    }

    /**
     * Divides this offset by the target offset (idempotent). The base offset won't be changed.
     * @param target the offset to be used in the division.
     * @return the new offset generated.
     */
    public Offset divide(Offset target) {
        return new Offset(x / target.getX(), y / target.getY());
    }

    /**
     * Multiplies this offset with the target offset (idempotent). The base offset won't be changed.
     * @param target the offset to be used in the multiplication.
     * @return the new offset generated.
     */
    public Offset multiply(Offset target) {
        return new Offset(x * target.getX(), y * target.getY());
    }


    public boolean atOrigin() {
        return x == 0 && y == 0;
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
        int offsetX = (int)(((double)offset.getX() / originSize.getWidth()) * targetSize.getWidth());
        offsetX -= offset.getX();
        int offsetY = (int)(((double)offset.getY() / originSize.getHeight()) * targetSize.getHeight());
        offsetY  -= offset.getY();

        System.out.println("New offset: " + offsetX + "," + offsetY);
        return new Offset(offsetX, offsetY);
    }
}
