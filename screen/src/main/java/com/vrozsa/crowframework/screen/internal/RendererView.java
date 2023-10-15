package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.shared.api.screen.Drawable;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.api.screen.RendererObserver;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.image.DrawingsSorter;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Display game-play objects in the screen.
 */
public class RendererView extends BaseView implements RendererObserver, Offsetable {
    public static final String NAME = "RENDERER_VIEW";
    private final List<Renderer> renderers;
    private final List<Renderer> persistentRenderers;
    protected Offset offset;

    public RendererView(final Rect rect) {
        this(NAME, rect);
    }

    public RendererView(final String name, final Rect rect) {
        super(name, rect);
        renderers = new ArrayList<>();
        persistentRenderers = new ArrayList<>();
        offset = Offset.origin();
    }

    public RendererView(final ViewTemplate viewTemplate) {
        super(viewTemplate);
        renderers = new ArrayList<>();
        persistentRenderers = new ArrayList<>();
    }

    public void addRenderer(final Renderer... renderers) {
        Arrays.stream(renderers).forEach(this::addRenderer);
    }

    public void addRenderer(final List<Renderer> renderers) {
        renderers.forEach(this::addRenderer);
    }

    public void addRenderer(Renderer renderer) {
        renderers.add(renderer);
        renderer.addRendererChangedListener(this);
    }

    /**
     * Persistent renderers wont be removed after removeAllRenderers is called.
     * @param renderer the renderer to be added.
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
     * Remove all renderers.
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

    /**
     * Sets the offset in which the elements from this view will be drawn. Because all elements will be given an offset,
     * this has an effect of 'moving' the camera'
     * @param offset offset to be set.
     */
    public void setOffset(final Offset offset) {
        this.offset = offset.clone();
    }

    /**
     * Gets a copy of the current offset.
     * @return current offset value.
     */
    public Offset getOffset() {
        return offset.clone();
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
            spriteSet.sort(new DrawingsSorter());
            spriteSet.forEach(s -> draw(s, g));
        }

        super.paintComponent(g);
    }

    private void draw(Drawable drawing, Graphics g) {
        if (!drawing.isEnabled() || drawing.getImage() == null) {
            return;
        }

        var renderer = drawing.getRenderer();
        var drawingOffset = drawing.getOffset();
        var scale = drawing.getScale();
        var size = drawing.getSize();

        Offset rendererPos = renderer.getPos();
        int screenPosX = rendererPos.getX() - offset.getX() + drawingOffset.getX();
        int screenPosY = rendererPos.getY() - offset.getY() + drawingOffset.getY();
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

    /**
     * Gets a map of drawings that has to be drawn ordered by its layers.
     * @param renderers the renderes from which the drawings will be taken.
     * @return the generated map of drawings by layers.
     */
    private Map<Integer, List<Drawable>> reduceRenderers(List<Renderer> renderers) {
        Map<Integer, List<Drawable>> sprites = new TreeMap<>();

        for (Renderer r : renderers) {
            if (r.isDisabled() || (r.getGameObject() != null && r.getGameObject().isInactive())) {
                continue;
            }

            if (!r.alwaysRender() && isRendererOutsideGameView(r)) {
                continue;
            }

            sprites.computeIfAbsent(r.getLayer(), l -> new ArrayList<>());

            sprites.get(r.getLayer()).addAll(r.getDrawings(true));
        }

        return sprites;
    }

    /**
     * Checks if the renderer center is outside visible in the current position.
     * @param r renderer to be checked.
     * @return true if outside game view; false if inside.
     */
    private boolean isRendererOutsideGameView(Renderer r) {
        Offset rendererPos = r.getPos();
        if (isPointInsideScreen(rendererPos.getX(), rendererPos.getY())) {
            return false;
        }

        var rSize = r.getSize();
        if (isPointInsideScreen(rendererPos.getX() + rSize.getWidth(), rendererPos.getY())) {
            return false;
        }

        if (isPointInsideScreen(rendererPos.getX(), rendererPos.getY() + rSize.getHeight())) {
            return false;
        }

        return !isPointInsideScreen(rendererPos.getX() + rSize.getWidth(), rendererPos.getY() + rSize.getHeight());
    }

    private boolean isPointInsideScreen(int x, int y) {
        int horBound = offset.getX() + rect.getWidth();
        int verBound = offset.getY() + rect.getHeight();
        return (x >= offset.getX() && x < horBound &&
                y >= offset.getY() && y < verBound);
    }
}
