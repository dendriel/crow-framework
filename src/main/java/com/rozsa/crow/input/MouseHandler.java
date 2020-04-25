package com.rozsa.crow.input;

import com.rozsa.crow.screen.attributes.Offset;

import java.awt.*;

public class MouseHandler {

    public static Offset getRelativePosition(Offset windowPosition) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY()).remove(windowPosition);
    }

    public static Offset getAbsolutePosition() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY());
    }
}
