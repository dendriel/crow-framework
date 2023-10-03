package com.vrozsa.crowframework;

import com.vrozsa.crowframework.shared.attributes.Offset;

import java.awt.MouseInfo;
import java.awt.Point;

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
