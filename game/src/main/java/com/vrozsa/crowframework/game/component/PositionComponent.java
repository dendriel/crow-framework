package com.vrozsa.crowframework.game.component;

import com.vrozsa.crowframework.shared.api.game.Position;
import com.vrozsa.crowframework.shared.api.game.PositionObserver;
import com.vrozsa.crowframework.shared.api.game.PositionOffsetObserver;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.attributes.Offset;

import java.util.ArrayList;
import java.util.List;

/**
 * Position locates the game object in the world. All game-objects have at least one mandatory position component.
 */
public final class PositionComponent extends AbstractComponent implements Position, PositionObserver {
    public static final String DEFAULT_POSITION = "_defaultPositionComponent";
    private Position parent;
    private final List<Position> children;
    private final List<PositionObserver> positionObservers;
    private final List<PositionOffsetObserver> offsetAddedObservers;
    private Vector pos;

    public PositionComponent(Vector pos) {
        this(pos, DEFAULT_POSITION);
    }

    public PositionComponent(final Vector pos, final String name) {
        this.pos = pos;
        this.name = name;

        children = new ArrayList<>();
        positionObservers = new ArrayList<>();
        offsetAddedObservers = new ArrayList<>();
    }

    public boolean isAt(final Vector pos) {
        return this.pos.equals(pos);
    }

    @Override
    public void update() {
        // no op.
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
        return pos.getOffset();
    }

    public void setOffset(final Offset offset) {
        pos.setOffset(offset);
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
        children.forEach(p -> ((PositionComponent)p).parentPositionChanged());
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

    private void addChild(final PositionComponent child) {
        children.add(child);
        addPositionChangedListener(child);
    }

    private void removeChild(final PositionComponent child) {
        children.remove(child);
        removePositionChangedListener(child);
    }

    public void setParent(final Position newParent) {
        if (parent != null) {
            ((PositionComponent)parent).removeChild(this);
        }

        parent = newParent;
        if (parent != null) {
            ((PositionComponent)parent).addChild(this);
            onPositionChanged();
        }
    }

    public Position getParent() {
        return parent;
    }

    public List<Position> getChildren() {
        return new ArrayList<>(children);
    }
}
