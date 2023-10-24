package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;
import com.vrozsa.crowframework.shared.attributes.Offset;

import java.awt.MouseInfo;

import static java.util.Objects.isNull;

/**
 * Provides mouse actions information.
 */
final class MouseHandler implements PointerHandler {

    private final OffsetGetter screenOffset;

    private MouseHandler(OffsetGetter screenOffset) {
        this.screenOffset = screenOffset;
    }

    public static MouseHandler create(OffsetGetter screenOffset) {
        return new MouseHandler(screenOffset);
    }

    @Override
    public Offset getPointerPosition() {
        var absolutePosition = getPointerAbsolutePosition();
        if (isNull(screenOffset)) {
            return absolutePosition;
        }
        return absolutePosition.sub(screenOffset.getOffset());
    }

    @Override
    public Offset getPointerAbsolutePosition() {
        var point = MouseInfo.getPointerInfo().getLocation();
        return new Offset((int)point.getX(), (int)point.getY());
    }
}
