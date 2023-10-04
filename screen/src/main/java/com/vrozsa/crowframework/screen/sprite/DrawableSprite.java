package com.vrozsa.crowframework.screen.sprite;

import com.vrozsa.crowframework.shared.api.screen.Drawable;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;

import java.util.Comparator;
import java.util.List;

public class DrawableSprite implements Drawable, Sprite {
    private final SpriteTemplate data;
    private int order;
    private Offset offset;
    private Scale scale;
    private Size size;
    private Image resizableImage;
    private boolean enabled;
    private boolean isFlipX;
    private Renderer renderer;
    private boolean isFlipY;

    public DrawableSprite(SpriteTemplate data) {
        this(data, data.isEnabled());
    }

    public DrawableSprite(SpriteTemplate data, boolean isEnabled) {
        this.data = data;
        reset();
        load();
        enabled = isEnabled;
    }

    public DrawableSprite(SpriteTemplate data, Image resizableImage, boolean isEnabled) {
        this.data = data;
        this.resizableImage = resizableImage;
        enabled = isEnabled;
        reset();
    }

    public static void sortByOrderAscending(List<Drawable> set) {
        set.sort(new SortDrawingsByOrder());
    }

    private void reset() {
        this.order = data.getOrder();
        this.enabled = data.isEnabled();
        this.isFlipX = data.isFlipX();
        this.isFlipY = data.isFlipY();
        this.scale = data.getScale();
        this.offset = data.getOffset();
        this.size = data.getSize();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Image getImage() {
        return resizableImage;
    }

    public int getOrder() {
        return order;
    }

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

    public Offset getDefaultOffset() {
        return data.getOffset();
    }

    public Scale getScale() {
        return scale;
    }

    public Scale getDefaultScale() {
        return data.getScale();
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public void setScaleWidth(double width) {
        this.scale.setWidth(width);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void load() {
        resizableImage = ImageLoader.load(data.getImageFile());
    }

    public boolean isFlipX() {
        return isFlipX;
    }

    public void setFlipX(boolean flipX) {
        isFlipX = flipX;
    }

    public boolean isFlipY() {
        return isFlipY;
    }

    public void setFlipY(boolean flipY) {
        isFlipY = flipY;
    }

    @Override
    public Size getSize() {
        return size.clone();
    }

    public void setSize(Size size) {
        this.size = size;
    }

    private static class SortDrawingsByOrder implements Comparator<Drawable> {
        @Override
        public int compare(Drawable o1, Drawable o2) {
            return o1.getOrder() - o2.getOrder();
        }
    }
}
