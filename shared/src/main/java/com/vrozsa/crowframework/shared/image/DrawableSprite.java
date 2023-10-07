package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Drawable;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;
import lombok.Data;


@Data
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
        this(data, data.enabled());
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

    private void reset() {
        this.order = data.order();
        this.enabled = data.enabled();
        this.isFlipX = data.isFlipX();
        this.isFlipY = data.isFlipY();
        this.scale = data.scale();
        this.offset = data.offset();
        this.size = data.size();
    }

    public Image getImage() {
        return resizableImage;
    }

    public Offset getDefaultOffset() {
        return data.offset();
    }


    public Scale getDefaultScale() {
        return data.scale();
    }

    public void setScaleWidth(double width) {
        this.scale.setWidth(width);
    }

    private void load() {
        resizableImage = ImageLoader.load(data.imageFile());
    }

    @Override
    public Size getSize() {
        return size.clone();
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
