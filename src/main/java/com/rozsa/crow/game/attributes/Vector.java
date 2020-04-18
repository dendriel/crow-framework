package com.rozsa.crow.game.attributes;

import com.rozsa.crow.screen.attributes.Offset;

import java.util.Objects;

public class Vector {
    private int x;
    private int y;
    private int z;

    public Vector() {}

    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector clone() {
        return new Vector(x, y, z);
    }

    public void setOffset(Offset offset) {
        x = offset.getX();
        y = offset.getY();
    }

    public void addOffset(Offset offset) {
        x += offset.getX();
        y += offset.getY();
    }

    public Offset getOffset() {
        return new Offset(x, y);
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

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public static Vector origin() {
        return new Vector(0,0,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x &&
                y == vector.y &&
                z == vector.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
