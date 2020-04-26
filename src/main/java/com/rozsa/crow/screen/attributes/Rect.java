package com.rozsa.crow.screen.attributes;

import java.util.Objects;

public class Rect {
    private Offset offset;
    private Size size;

    public Rect() {
        offset = new Offset();
        size = new Size();
    }

    public Rect(Offset offset, Size size) {
        this.offset = offset;
        this.size = size;
    }

    public Rect(int x, int y, int width, int height) {
        offset = new Offset(x, y);
        size = new Size(width, height);
    }

    public int getX() {
        return offset.getX();
    }

    public void setX(int x) {
        offset.setX(x);
    }

    public int getY() {
        return offset.getY();
    }

    public void setY(int y) {
        offset.setY(y);
    }

    public int getWidth() {
        return size.getWidth();
    }

    public void setWidth(int width) {
        size.setWidth(width);
    }

    public Rect add(Offset targetOffset) {
        return new Rect(offset.add(targetOffset), size);
    }

    public Rect add(Size targetSize) {
        return new Rect(offset, size.add(targetSize));
    }

    public void addWidth(int width) {
        size.addWidth(width);
    }

    public void addHeight(int height) {
        size.addHeight(height);
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void setHeight(int height) {
        size.setHeight(height);
    }

    public void setOffset(Offset newOffset) {
        offset = newOffset;
    }

    public void setSize(Size newSize) {
        size = newSize;
    }

    public Size getSize() {
        return size.clone();
    }

    public Offset getOffset() {
        return offset.clone();
    }

    public Rect clone() {
        return new Rect(offset.clone(), size.clone());
    }

    @Override
    public String toString() {
        return String.format("%s - %s", offset, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return offset.equals(rect.offset) &&
                size.equals(rect.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, size);
    }
}
