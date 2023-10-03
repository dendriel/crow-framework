package com.vrozsa.crowframework.screen.sprite;

import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;

public class SpriteTemplate {
    private String imageFile;
    private int order;
    private Offset offset;
    private Scale scale;
    private Size size;
    private Boolean enabled;
    private Boolean isFlipX;
    private Boolean isFlipY;

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
        clone.setSize(size.clone());

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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isFlipX() {
        return isFlipX != null ? isFlipX : false;
    }

    public void setFlipX(Boolean flipX) {
        isFlipX = flipX;
    }

    public Boolean isFlipY() {
        return isFlipY != null ? isFlipY : false;
    }

    public void setFlipY(Boolean flipY) {
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

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
