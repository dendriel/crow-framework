package com.rozsa.screen.ui;

import com.rozsa.shared.api.screen.Image;
import com.rozsa.screen.sprite.ImageLoader;
import com.rozsa.shared.attributes.Rect;
import com.rozsa.shared.attributes.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UIIcon extends UIBaseComponent<UIIconTemplate> {
    private UIIconTemplate data;
    protected Image resizableImage;

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
        resizableImage = ImageLoader.load(imageFile);
    }

    private void setup() {
        resizableImage = ImageLoader.load(data.getImageFile());
        rect = data.getRect();
    }

    public Rect getRect() {
        return rect.clone();
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Image getImage() {
        return resizableImage;
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
        return resizableImage.getContent(rect.getWidth(), rect.getHeight());
    }
}
