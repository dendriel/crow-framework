package com.vrozsa.crowframework.game.component;

import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.api.game.PositionObserver;
import com.vrozsa.crowframework.shared.api.game.PositionOffsetObserver;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.attributes.Offset;

import java.util.ArrayList;
import java.util.List;

public class Position extends AbstractComponent implements PositionComponent, PositionObserver {
    public static final String DEFAULT_POSITION = "_defaultPositionComponent";
    private PositionComponent parent;
    private final List<Position> children;
    private final List<PositionObserver> positionObservers;
    private final List<PositionOffsetObserver> offsetAddedObservers;
    private Vector pos;

    public Position(Vector pos) {
        this(pos, DEFAULT_POSITION);
    }

    public Position(final Vector pos, final String positionCompName) {
        this.pos = pos;

        children = new ArrayList<>();
        positionObservers = new ArrayList<>();
        offsetAddedObservers = new ArrayList<>();
        name = positionCompName;
    }

    public boolean isAt(Vector pos) {
        return this.pos.equals(pos);
    }

    @Override
    public void wrapUp() {
        super.wrapUp();
    }

    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public int getZ() {
        return pos.getZ();
    }

    public Offset getOffset() {
        return pos.getOffset().clone();
    }

    public Vector getVector() {
        return pos.clone();
    }

    public void setPosition(final int x, final int y) {
        pos.setX(x);
        pos.setY(y);
        onPositionChanged();
    }

    public void setPositionRelativeToParent(int x, int y) {
        int parentPosX = parent != null ? parent.getX() : 0;
        int parentPosY = parent != null ? parent.getY() : 0;

        pos.setX(x + parentPosX);
        pos.setY(y + parentPosY);
        onPositionChanged();
    }

    public void setAbsolutePosition(Offset pos) {
        setAbsolutePosition(pos.getX(), pos.getY());
    }

    public void setAbsolutePosition(int x, int y) {
        int parentPosX = parent != null ? parent.getX() : 0;
        int parentPosY = parent != null ? parent.getY() : 0;
        pos.setX(x - parentPosX);
        pos.setY(y - parentPosY);
        onPositionChanged();
    }

    public Offset getAbsolutePosition() {
        int parentPosX = parent != null ? parent.getX() : 0;
        int parentPosY = parent != null ? parent.getY() : 0;

        return new Offset(pos.getX() + parentPosX, pos.getY() + parentPosY);
    }

    /**
     * Sets the position relative to the parent; or the position relative to the map (absolute)
     * if there is no parent.
     */
    public void setPosition(final Vector pos) {
        this.pos = pos;
        onPositionChanged();
    }

    public void setOffset(final Offset offset) {
        this.pos.setOffset(offset);
    }

    public void addOffset(final Offset offset) {
        if (offset.atOrigin()) {
            return;
        }
        pos.addOffset(offset);
        onPositionChanged();
        onOffsetAdded(offset.getX(), offset.getY());
    }

    public void addPositionChangedListener(PositionObserver observer) {
        positionObservers.add(observer);
    }

    public void removePositionChangedListener(PositionObserver observer) {
        positionObservers.remove(observer);
    }

    private void onPositionChanged() {
        positionObservers.forEach(o -> o.positionChanged(getAbsolutePosX(), getAbsolutePosY()));
        children.forEach(Position::parentPositionChanged);
    }

    public void addPositionOffsetAddedListener(PositionOffsetObserver observer) {
        offsetAddedObservers.add(observer);
    }

    private void onOffsetAdded(int offsetX, int offsetY) {
        offsetAddedObservers.forEach(o -> o.offsetAdded(offsetX, offsetY));
    }

    @Override
    public void positionChanged(int newPosX, int newPosY) {
        onPositionChanged();
    }

    private void parentPositionChanged() {
        onPositionChanged();
    }

    public int getAbsolutePosX() {
        return getParentPosX() + pos.getX();
    }

    public int getAbsolutePosY() {
        return getParentPosY() + pos.getY();
    }

    private int getParentPosX() {
        return parent != null ? parent.getAbsolutePosX() : 0;
    }

    private int getParentPosY() {
        return parent != null ? parent.getAbsolutePosY() : 0;
    }

    @Override
    public String toString() {
        return pos.toString();
    }

    private void addChild(Position child) {
        children.add(child);
        addPositionChangedListener(child);
    }

    private void removeChild(Position child) {
        children.remove(child);
        removePositionChangedListener(child);
    }

    public void setParent(PositionComponent newParent) {
        if (parent != null) {
            ((Position)parent).removeChild(this);
        }

        parent = newParent;
        if (parent != null) {
            ((Position)parent).addChild(this);
            onPositionChanged();
        }
    }

    public PositionComponent getParent() {
        return parent;
    }

    public List<PositionComponent> getChildren() {
        return new ArrayList<>(children);
    }

    public Position getChildByName(String name) {
        return children
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
