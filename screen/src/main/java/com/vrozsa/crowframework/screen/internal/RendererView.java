package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.screen.sprite.Sprite;
import com.vrozsa.crowframework.shared.api.screen.RendererObserver;
import com.vrozsa.crowframework.shared.api.screen.Renderer;

import com.vrozsa.crowframework.shared.api.screen.Drawable;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.Graphics;
import java.util.List;
import java.util.*;

/**
 * Allow to display game-play objects in the screen (in addition to UI components).
 */
public class RendererView extends BaseView implements RendererObserver {
    private List<Renderer> renderers;
    private List<Renderer> persistentRenderers;
    protected int offsetX;
    protected int offsetY;

    public RendererView(Rect rect) {
        super(rect);
        renderers = new ArrayList<>();
        persistentRenderers = new ArrayList<>();
    }

    public RendererView(ViewTemplate viewTemplate) {
        super(viewTemplate);
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

    public Offset getScreenOffset() {
        return new Offset(offsetX, offsetY);
    }

    @Override
    public void rendererChanged(Renderer renderer) {
        draw();
    }

    @Override
    protected void paintComponent(Graphics g) {
        List<Renderer> tempRenderers = new ArrayList<>(renderers);

        Map<Integer, List<Drawable>> sprites = reduceRenderers(tempRenderers);

        for (List<Drawable> spriteSet : sprites.values()) {
            Sprite.sortByOrderAscending(spriteSet);
            spriteSet.forEach(s -> draw(s, g));
        }

        super.paintComponent(g);
    }

    private void draw(Drawable drawing, Graphics g) {
        if (!drawing.isEnabled() || drawing.getImage() == null) {
            return;
        }

        Renderer renderer = drawing.getRenderer();
        Offset offset = drawing.getOffset();
        Scale scale = drawing.getScale();
        Size size = drawing.getSize();

        Offset rendererPos = renderer.getPos();
        int screenPosX = rendererPos.getX() - offsetX + offset.getX();
        int screenPosY = rendererPos.getY() - offsetY + offset.getY();
        int width = (int)(size.getWidth() * scale.getWidth());
        int height = (int)(size.getHeight() * scale.getHeight());

        if (renderer.isFlipX() || drawing.isFlipX()) {
            boolean flipTwice = renderer.isFlipX() && drawing.isFlipX();
            width *= flipTwice ? 1 : -1;
            screenPosX += flipTwice ? 0 : size.getWidth();
        }

        if (renderer.isFlipY() || drawing.isFlipY()) {
            boolean flipTwice = renderer.isFlipY() && drawing.isFlipY();
            height *= flipTwice ? 1 : -1;
            screenPosY += flipTwice ? 0 : size.getHeight();
        }


        int absWidth = Math.abs(width);
        int absHeight = Math.abs(height);

        g.drawImage(drawing.getImage().getContent(absWidth, absHeight), screenPosX, screenPosY, width, height, this);
    }

    private Map<Integer, List<Drawable>> reduceRenderers(List<Renderer> renderers) {
        Map<Integer, List<Drawable>> sprites = new TreeMap<>();

        for (Renderer r : renderers) {
            if (r.isDisabled() || (r.getGameObject() != null && r.getGameObject().isInactive())) {
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
        Offset rendererPos = r.getPos();
        if (isPointInsideScreen(rendererPos.getX(), rendererPos.getY())) {
            return false;
        }

        Size rSize = r.getSize();
        if (isPointInsideScreen(rendererPos.getX() + rSize.getWidth(), rendererPos.getY())) {
            return false;
        }

        if (isPointInsideScreen(rendererPos.getX(), rendererPos.getY() + rSize.getHeight())) {
            return false;
        }

        return !isPointInsideScreen(rendererPos.getX() + rSize.getWidth(), rendererPos.getY() + rSize.getHeight());
    }

    private boolean isPointInsideScreen(int x, int y) {
        int horBound = offsetX + rect.getWidth();
        int verBound = offsetY + rect.getHeight();
        return (x >= offsetX && x < horBound &&
                y >= offsetY && y < verBound);
    }
}
