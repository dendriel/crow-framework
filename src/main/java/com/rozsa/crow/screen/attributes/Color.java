package com.rozsa.crow.screen.attributes;

import java.util.Objects;

public class Color {
    private int r;
    private int g;
    private int b;

    public Color() {
        r = 255;
        g = 255;
        b = 255;
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public java.awt.Color getJColor() {
        return new java.awt.Color(r, g, b);
    }

    public Color clone() {
        return new Color(r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color colorData = (Color) o;
        return r == colorData.r &&
                g == colorData.g &&
                b == colorData.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }
}
