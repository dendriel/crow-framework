package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.attributes.Offset;

import java.awt.MouseInfo;

public class MouseHandler {

    public static Offset getRelativePosition(Offset windowPosition) {
        var point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY()).sub(windowPosition);
    }

    public static Offset getAbsolutePosition() {
        var point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY());
    }
}
