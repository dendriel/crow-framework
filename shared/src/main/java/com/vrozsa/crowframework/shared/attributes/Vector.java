package com.vrozsa.crowframework.shared.attributes;

import lombok.Data;


@Data
public class Vector implements Cloneable {
    private int x;
    private int y;
    private int z;

    public Vector() {}

    private Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector of(int x, int y, int z) {
        return new Vector(x, y, z);
    }

    public static Vector of(final Offset offset) {
        return new Vector(offset.getX(), offset.getY(), 0);
    }

    /**
     * Generates a new instance equivalent to the instance used to invoke clone.
     * @return the cloned instance.
     */
    @Override
    public Vector clone() {
        return new Vector(x, y, z);
        // TODO: default Object.clone() should work for primitives.
//        return (Vector)super.clone();
    }

    public void setOffset(Offset offset) {
        x = offset.getX();
        y = offset.getY();
    }

    public void addOffset(final Offset offset) {
        x += offset.getX();
        y += offset.getY();
    }

    public Offset getOffset() {
        return new Offset(x, y);
    }

    public static Vector origin() {
        return new Vector(0,0,0);
    }
}
