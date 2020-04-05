package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UIIcon extends UIBaseComponent<UIIconTemplate> {
    private UIIconTemplate data;
    protected Image image;

    public UIIcon(UIIconTemplate data) {
        super(data);
        this.data = data;
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

        Rect rectToPaint = getRectToPaint();
        g.drawImage(getImageToPaint(), rectToPaint.getX(), rectToPaint.getY(), rectToPaint.getWidth(), rectToPaint.getHeight(), observer);
    }

    protected Rect getRectToPaint() {
        Rect rectToPaint = rect.clone();
        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();

        rectToPaint.setX(x);
        rectToPaint.setY(y);
        return rectToPaint;
    }

    protected BufferedImage getImageToPaint() {
        return image.getContent(rect.getWidth(), rect.getHeight());
    }
}
