package com.vrozsa.crowframework.shared.attributes;

import java.util.Objects;

// TODO: make this immutable?
public class Size implements Comparable<Size> {
    public static Size zeroed() {
        return new Size();
    }

    private int width;
    private int height;

    public Size() {
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size of(final int width, final int height) {
        return new Size(width, height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void addWidth(int width) {this.width += width; }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addHeight(int height) { this.height += height;}

    public Size add(Size target) {
        int newW = width + target.width;
        int newH = height + target.height;
        return new Size(newW, newH);
    }

    public Size plus(Size target) {
        int newW = width * target.getWidth();
        int newH = height * target.getHeight();
        return new Size(newW, newH);
    }

    public static Size updateSize(Size size, Size originSize, Size newSize) {
        int width = (int)(((float)size.getWidth() / originSize.getWidth()) * newSize.getWidth());
        int height = (int)(((float)size.getHeight() / originSize.getHeight()) * newSize.getHeight());

        return new Size(width, height);
    }

    public boolean isLargerThan(Size size) {
        return this.width > size.getWidth() &&
                this.height > size.getHeight();
    }

    public Size clone() {
        return new Size(width, height);
    }

    @Override
    public String toString() {
        return String.format("w: %d h: %d", width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return width == size.width &&
                height == size.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public int compareTo(Size o) {
        if (super.equals(o)) {
            return 0;
        }

        if (this.width > o.width) {
            return 1;
        }
        else if (this.width < o.width) {
            return -1;
        }

        if (this.height > o.height) {
            return 1;
        }
        else if (this.height < o.height) {
            return -1;
        }

        return 0;
    }
}
