package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UIIcon extends UIBaseComponent<UIIconTemplate> {
    private UIIconTemplate data;
    private Rect rect;
    private Image image;
    private Size expandFactor;

    public UIIcon(UIIconTemplate data) {
        this(data, new Size());
    }

    public UIIcon(UIIconTemplate data, Size expandFactor) {
        this.data = data;
        this.expandFactor = expandFactor;
        setup();
    }

    public void updateComponentTemplate(UIIconTemplate data) {
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setup();
    }

    public void setImage(String imageFile) {
        image = Image.load(imageFile);
    }

    private void setup() {
        image = Image.load(data.getImageFile());
        rect = data.getRect();
        rect.addWidth(expandFactor.getWidth());
        rect.addHeight(expandFactor.getHeight());
    }

    public Rect getRect() {
        return rect.clone();
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Image getImage() {
        return image;
    }

    public void setSize(Size size) {
        rect.setWidth(size.getWidth());
        rect.setHeight(size.getHeight());
    }

    @Override
    public void show() {
        isEnabled = true;
    }

    @Override
    public void hide() {
        isEnabled = false;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;

        g.drawImage(image.getContent(rect.getWidth(), rect.getHeight()), rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), observer);
    }
}
