package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.attributes.Offset;

import java.awt.MouseInfo;

/**
 * Provides mouse actions information.
 */
final class MouseHandler implements PointerHandler {

    private MouseHandler() {}

    public static MouseHandler create() {
        return new MouseHandler();
    }

    @Override
    public Offset getPointerPosition() {
        var point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY());
    }
}
