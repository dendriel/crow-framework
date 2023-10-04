package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Drawable;

import java.util.Comparator;

public class DrawingsSorter implements Comparator<Drawable> {

    @Override
    public int compare(Drawable o1, Drawable o2) {
        return o1.getOrder() - o2.getOrder();
    }
}
