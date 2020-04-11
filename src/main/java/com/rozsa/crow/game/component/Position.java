package com.rozsa.crow.game.component;

import com.rozsa.crow.game.api.PositionObserver;
import com.rozsa.crow.game.attributes.Vector;
import com.rozsa.crow.screen.attributes.Offset;

import java.util.ArrayList;
import java.util.List;

public class Position extends BaseComponent implements PositionObserver {
    private Position parent;

    private final List<Position> children;

    private List<PositionObserver> positionObservers;

    private Vector pos;

    public Position(Vector pos, String positionCompName) {
        this.pos = pos;

        children = new ArrayList<>();
        positionObservers = new ArrayList<>();
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

    public Offset getOffset() {
        return pos.getOffset();
    }

    public Vector getVector() {
        return pos;
    }

    public void setPosition(int x, int y) {
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
    public void setPosition(Offset offset) {
        pos.setOffset(offset);
        onPositionChanged();
    }

    public void addOffset(Offset offset) {
        pos.addOffset(offset);
        onPositionChanged();
    }

    public void addPositionChangedListener(PositionObserver observer) {
        positionObservers.add(observer);
    }

    public void removePositionChangedListener(PositionObserver observer) {
        positionObservers.remove(observer);
    }

    private void onPositionChanged() {
        positionObservers.forEach(o -> o.positionChanged(getAbsolutePosX(), getAbsolutePosY()));
        children.forEach(c -> c.parentPositionChanged());
    }

    @Override
    public void positionChanged(int newPosX, int newPosY) {
        onPositionChanged();
    }

    private void parentPositionChanged() {
        onPositionChanged();
    }

    private int getAbsolutePosX() {
        return getParentPosX() + pos.getX();
    }

    private int getAbsolutePosY() {
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

    public void setParent(Position newParent) {
        if (parent != null) {
            parent.removeChild(this);
        }

        parent = newParent;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public Position getParent() {
        return parent;
    }

    public List<Position> getChildren() {
        return children;
    }

    public Position getChildByName(String name) {
        return children
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
