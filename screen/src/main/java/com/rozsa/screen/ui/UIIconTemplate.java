package com.rozsa.screen.ui;

public class UIIconTemplate extends UIBaseComponentTemplate {
    private String imageFile;

    public UIIconTemplate() {
        super(UIComponentType.ICON);
    }

    public UIIconTemplate(UIComponentType type) {
        super(type);
    }


    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
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
