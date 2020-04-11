package com.rozsa.crow.screen;

import com.rozsa.crow.game.api.RendererObserver;
import com.rozsa.crow.game.component.Renderer;
import com.rozsa.crow.screen.api.Drawable;
import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Scale;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Sprite;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Allow to display game-play objects in the screen (in addition to UI components).
 */
public class RendererView extends BaseView implements RendererObserver {
    private List<Renderer> renderers;
    private List<Renderer> persistentRenderers;
    protected Size tileSize;
    protected int offsetX;
    protected int offsetY;

    public RendererView(Rect rect, Size tileSize, Size tileSizeFactor) {
        super(rect.add(tileSizeFactor));
        this.tileSize = tileSize;
        renderers = new ArrayList<>();
        persistentRenderers = new ArrayList<>();
    }

    public void addRenderer(Renderer... renderers) {
        Arrays.stream(renderers).forEach(this::addRenderer);
    }

    public void addRenderer(List<Renderer> renderers) {
        renderers.forEach(this::addRenderer);
    }

    public void addRenderer(Renderer renderer) {
        renderers.add(renderer);
        renderer.addRendererChangedListener(this);
    }

    /**
     * Persistent renderers wont be removed after removeAllRenderers is called.
     * @param renderer
     */
    public void addPersistentRenderer(Renderer renderer) {
        persistentRenderers.add(renderer);
        addRenderer(renderer);
    }

    public void removeRenderer(Renderer renderer) {
        renderers.remove(renderer);
        renderer.removeRendererChangedListener(this);
    }

    public void removeRenderers(List<Renderer> renderers) {
        renderers.forEach(this::removeRenderer);
    }

    /**
     * Won't remove persistent renderers.
     */
    public void removeAllRenderers() {
        for(Renderer r : renderers) {
            r.removeRendererChangedListener(this);
        }
        renderers.clear();
        resetPersistentRenderers();
    }

    private void resetPersistentRenderers() {
        persistentRenderers.forEach(this::addRenderer);
    }

    public void setScreenOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void rendererChanged(Renderer renderer) {
        draw();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Renderer> tempRenderers = new ArrayList<>(renderers);

        Map<Integer, List<Drawable>> sprites = reduceRenderers(tempRenderers);

        for (List<Drawable> spriteSet : sprites.values()) {
            Sprite.sortByOrderAscending(spriteSet);
            spriteSet.forEach(s -> draw(s, g));
        }
    }

    private void draw(Drawable drawing, Graphics g) {
        if (!drawing.isEnabled() || drawing.getImage() == null) {
            return;
        }

        Renderer renderer = drawing.getRenderer();
        Offset offset = drawing.getOffset();
        Scale scale = drawing.getScale();
        int screenPosX = renderer.getX() * tileSize.getWidth() - offsetX + offset.getX();
        int screenPosY = renderer.getY() * tileSize.getHeight() - offsetY + offset.getY();
        int width = (int)(tileSize.getWidth() * scale.getWidth());
        int height = (int)(tileSize.getHeight() * scale.getHeight());

        if (renderer.isFlipX() || drawing.isFlipX()) {
            boolean flipTwice = renderer.isFlipX() && drawing.isFlipX();
            width *= flipTwice ? 1 : -1;
            screenPosX += flipTwice ? 0 : tileSize.getWidth();
        }

        if (renderer.isFlipY() || drawing.isFlipY()) {
            boolean flipTwice = renderer.isFlipY() && drawing.isFlipY();
            width *= flipTwice ? 1 : -1;
            screenPosX += flipTwice ? 0 : tileSize.getWidth();
        }


        int absWidth = Math.abs(width);

        g.drawImage(drawing.getImage().getContent(absWidth, height), screenPosX, screenPosY, width, height, this);
    }

    private Map<Integer, List<Drawable>> reduceRenderers(List<Renderer> renderers) {
        Map<Integer, List<Drawable>> sprites = new TreeMap<>();

        for (Renderer r : renderers) {
            if (r.isDisabled() || r.getGameObject().isInactive()) {
                continue;
            }

            if (isRendererOutsideGameView(r)) {
                continue;
            }

            if (!sprites.containsKey(r.getLayer())) {
                sprites.put(r.getLayer(), new ArrayList<>());
            }

            sprites.get(r.getLayer()).addAll(r.getDrawings(true));
        }

        return sprites;
    }

    private boolean isRendererOutsideGameView(Renderer r) {
        int screenPosX = r.getX() * tileSize.getWidth() - offsetX;
        int screenPosY = r.getY() * tileSize.getHeight() - offsetY;

        if (screenPosX < 0 || screenPosX >= rect.getWidth() ||
                screenPosY < 0 || screenPosY >= rect.getHeight()) {
            return true;
        }

        return false;
    }
}
