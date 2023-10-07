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
    private final SpriteTemplate template;
    private int order;
    private Offset offset;
    private Scale scale;
    private Size size;
    private Image image;
    private boolean enabled;
    private boolean isFlipX;
    private Renderer renderer;
    private boolean isFlipY;

    public DrawableSprite(final SpriteTemplate template, final Image image, boolean enabled) {
        this.template = template;
        this.image = image;
        reset(enabled);
    }

    public DrawableSprite(final SpriteTemplate template) {
        this.template = template;
        reset(template.enabled());
        load();
    }

    private void load() {
        image = ImageLoader.load(template.imageFile());
    }

    private void reset(final boolean enabled) {
        this.enabled = enabled;
        this.order = template.order();
        this.isFlipX = template.isFlipX();
        this.isFlipY = template.isFlipY();
        this.scale = template.scale();
        this.offset = template.offset();
        this.size = template.size();
    }

    public Image getImage() {
        return image;
    }

    public Offset getDefaultOffset() {
        return template.offset();
    }


    public Scale getDefaultScale() {
        return template.scale();
    }

    public void setScaleWidth(double width) {
        this.scale.setWidth(width);
    }

    @Override
    public Size getSize() {
        return size.clone();
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
