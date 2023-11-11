package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.input.PointerObserver;
import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;
import com.vrozsa.crowframework.shared.attributes.Offset;

import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Provides mouse actions information.
 */
final class MouseHandler implements PointerHandler, PointerListener {
    private final OffsetGetter screenOffset;
    private final List<PointerObserver> clickObservers;
    private final List<PointerObserver> pressObservers;
    private final List<PointerObserver> releaseObservers;

    private MouseHandler(OffsetGetter screenOffset) {
        this.screenOffset = screenOffset;
        clickObservers = new ArrayList<>();
        pressObservers = new ArrayList<>();
        releaseObservers = new ArrayList<>();
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

    @Override
    public void onPointerClicked(Offset pos) {
        clickObservers.forEach(o -> o.onPointerEvent(pos));
    }

    @Override
    public void onPointerPressed(Offset pos) {
        pressObservers.forEach(o -> o.onPointerEvent(pos));
    }

    @Override
    public void onPointerReleased(Offset pos) {
        releaseObservers.forEach(o -> o.onPointerEvent(pos));
    }

    @Override
    public void addPointerClickedObserver(PointerObserver observer) {
        clickObservers.add(observer);
    }

    @Override
    public void removePointerClickedObserver(PointerObserver observer) {
        clickObservers.remove(observer);
    }

    @Override
    public void addPointerPressedObserver(PointerObserver observer) {
        pressObservers.add(observer);
    }

    @Override
    public void removePointerPressedObserver(PointerObserver observer) {
        pressObservers.remove(observer);
    }

    @Override
    public void addPointerReleasedObserver(PointerObserver observer) {
        releaseObservers.add(observer);
    }

    @Override
    public void removePointerReleasedObserver(PointerObserver observer) {
        releaseObservers.remove(observer);
    }

}
