package com.rozsa.crow.game.component;

import com.rozsa.crow.game.api.PositionObserver;
import com.rozsa.crow.game.api.RendererObserver;
import com.rozsa.crow.screen.api.Drawable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Renderer extends BaseComponent implements PositionObserver {
    public static String DEFAULT_RENDERER = "_defaultRendererComponent";
    private List<Drawable> drawings;
    private int layer;
    private int posX;
    private int posY;
    private boolean flipX;
    private boolean flipY;

    private Set<RendererObserver> observers;

    public Renderer(Position position, int layer, String name, boolean flipX, boolean flipY) {
        this(position, layer, true, name, flipX, flipY);
    }

    public Renderer(Position position, int layer, String name, boolean flipX, boolean flipY, Drawable... drawings) {
        this(position, layer, true, name, flipX, flipY, drawings);
    }

    public Renderer(Position position, int layer, boolean isEnabled, String name, boolean flipX, boolean flipY, Drawable... drawings) {
        super(isEnabled, name);
        position.addPositionChangedListener(this);
        posX = position.getX();
        posY = position.getY();
        this.layer = layer;
        this.flipX = flipX;
        this.flipY = flipY;
        this.drawings = new ArrayList<>();
        initializeDrawings(drawings);

        observers = new HashSet<>();
    }

    public void addRendererChangedListener(RendererObserver observer) {
        observers.add(observer);
    }

    public void removeRendererChangedListener(RendererObserver observer) {
        observers.remove(observer);
    }

    private void initializeDrawings(Drawable[] sprites) {
        for (Drawable s : sprites) {
            addDrawing(s);
        }
    }

    @Override
    public void positionChanged(int newPosX, int newPosY) {
        posX = newPosX;
        posY = newPosY;
        onRendererChanged();
    }

    private void onRendererChanged() {
        observers.forEach(o -> o.rendererChanged(this));
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public List<Drawable> getDrawings(boolean filterInactive) {
        if (filterInactive) {
            return new ArrayList<>(drawings);
        }
        return drawings.stream().filter(Drawable::isEnabled).collect(Collectors.toList());
    }

    public void addDrawing(Drawable drawing) {
        drawings.add(drawing);
        drawing.setRenderer(this);
    }

    public void clearDrawings() {
        drawings.forEach(d -> d.setRenderer(null));
        drawings.clear();
    }

    public void disableAllDrawings() {
        drawings.forEach(d -> d.setEnabled(false));
    }

    public void enableDrawing(int index) {
        if (drawings.size() <= index) {
            return;
        }

        drawings.get(index).setEnabled(true);
    }

    public List<Integer> getEnabledDrawingsIndex() {
        List<Integer> enabled = new ArrayList<>();
        for (int i = 0; i < drawings.size(); i++) {
            if (drawings.get(i).isEnabled()) {
                enabled.add(i);
            }
        }

        return enabled;
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        onRendererChanged();
    }

    @Override
    public String toString() {
        return String.format("Renderer: %s", name);
    }
}
