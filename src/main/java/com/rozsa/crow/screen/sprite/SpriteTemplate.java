package com.rozsa.crow.screen.sprite;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Scale;

public class SpriteTemplate {
    private String imageFile;
    private int order;
    private Offset offset;
    private Scale scale;
    private boolean enabled;
    private boolean isFlipX;
    private boolean isFlipY;

    public SpriteTemplate() {
        offset = new Offset();
        scale = new Scale(1, 1);
    }

    @Override
    public SpriteTemplate clone() {
        SpriteTemplate clone = new SpriteTemplate();

        clone.setImageFile(imageFile);
        clone.setOrder(order);
        clone.setOffset(offset.clone());
        clone.setScale(scale.clone());
        clone.setEnabled(enabled);
        clone.setFlipX(isFlipX);
        clone.setFlipY(isFlipY);

        return clone;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Scale getScale() {
        return scale.clone();
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Offset getOffset() {
        return offset.clone();
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }

}
