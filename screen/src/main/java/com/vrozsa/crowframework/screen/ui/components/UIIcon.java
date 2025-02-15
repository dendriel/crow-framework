package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.image.ImageLoader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UIIcon extends AbstractUIComponent<UIIconTemplate> {
    private UIIconTemplate data;
    protected Image resizableImage;

    public static UIIcon from(final UIIconTemplate data) {
        return new UIIcon(data);
    }

    protected UIIcon(UIIconTemplate data) {
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
        if (!isEnabled){
            return;
        }

        var rectToPaint = getRectToPaint();
        g.drawImage(getImageToPaint(), rectToPaint.getX(), rectToPaint.getY(), rectToPaint.getWidth(), rectToPaint.getHeight(), observer);
    }

    protected Rect getRectToPaint() {
        var rectToPaint = rect.clone();
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
