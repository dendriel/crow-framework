package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;

public class UIIconTemplate {
    private String imageFile;
    private Rect rect;

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        UIIconTemplate other = (UIIconTemplate) obj;
        if (other == null) {
            return false;
        }

        boolean isEqual = imageFile.equals(other.getImageFile());
        isEqual &= rect.equals(other.getRect());

        return isEqual;
    }
}
