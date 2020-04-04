package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.ui.api.UIFillBar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UIVerticalSlideBar extends UIBaseComponent<UISlideBarTemplate> implements UIFillBar {
    private final UISlideBarTemplate data;

    private final Image foreground;

    private final Image background;

    private float fill;

    public UIVerticalSlideBar(UISlideBarTemplate data) {
        super(data);
        this.data = data;
        fill = 1f;

        background = Image.load(data.getBackgroundImage());
        foreground = Image.load(data.getForegroundImage());
    }

    @Override
    public void setFill(float value) {
        value = Math.max(0, value);
        value = Math.min(1, value);
        fill = value;

        onComponentChanged();
    }

    public void show() {
        isEnabled = true;
    }

    public void hide() {
        isEnabled = false;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;

        Rect rect = data.getForegroundRect();
        Rect bgRect = getBackgroundRect(data.getBackgroundRect());
        g.drawImage(background.getContent(rect.getWidth(), rect.getHeight()), bgRect.getX(), bgRect.getY(), bgRect.getWidth(), bgRect.getHeight(), observer);
        g.drawImage(foreground.getContent(rect.getWidth(), rect.getHeight()), rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), observer);
    }

    @Override
    public void updateComponentTemplate(UISlideBarTemplate data) {
        throw new NotImplementedException();
    }

    private Rect getBackgroundRect(Rect rect) {
        int height = (int) (rect.getHeight() * fill);
        int y = rect.getY() + (rect.getHeight() - height);
        return new Rect(rect.getX(), y, rect.getWidth(), height);
    }
}
