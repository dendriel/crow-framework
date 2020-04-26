package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.ui.api.UIFillBar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.image.ImageObserver;

public class UISlider extends UIBaseComponent<UISliderTemplate> implements UIFillBar {
    private final UISliderTemplate data;
    private final Image dynamicImage;
    private final Image staticImage;
    private final Type sliderType;
    private final boolean reverseFill;

    private Rect staticRect;
    private Rect dynamicRect;
    private float fill;

    public UISlider(UISliderTemplate data) {
        super(data);
        this.data = data;
        sliderType = data.getSliderType();
        reverseFill = data.isReverseFill();
        fill = data.getFill();

        staticImage = Image.load(data.getStaticImage());
        dynamicImage = Image.load(data.getDynamicImage());

        setup();
    }

    private void setup() {
        rect = data.getRect();
        dynamicRect = data.getDynamicRect();
        staticRect = data.getStaticRect();
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

        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();
        Rect newDynamicRect = getBackgroundRect(dynamicRect);

        if (data.isStaticImageAbove()) {
            g.drawImage(dynamicImage.getContent(dynamicRect.getWidth(), dynamicRect.getHeight()), newDynamicRect.getX() + x, newDynamicRect.getY() + y, newDynamicRect.getWidth(), newDynamicRect.getHeight(), observer);
            g.drawImage(staticImage.getContent(staticRect.getWidth(), staticRect.getHeight()), staticRect.getX() + x, staticRect.getY() + y, staticRect.getWidth(), staticRect.getHeight(), observer);
        }
        else {
            g.drawImage(staticImage.getContent(staticRect.getWidth(), staticRect.getHeight()), staticRect.getX() + x, staticRect.getY() + y, staticRect.getWidth(), staticRect.getHeight(), observer);
            g.drawImage(dynamicImage.getContent(dynamicRect.getWidth(), dynamicRect.getHeight()), newDynamicRect.getX() + x, newDynamicRect.getY() + y, newDynamicRect.getWidth(), newDynamicRect.getHeight(), observer);
        }
    }

    @Override
    public void updateComponentTemplate(UISliderTemplate data) {
        throw new NotImplementedException();
    }

    private Rect getBackgroundRect(Rect rect) {
        if (sliderType == Type.VERTICAL) {
            int height = (int) (rect.getHeight() * fill);
            int y = rect.getY() + ( reverseFill ? rect.getHeight() - height : 0);
            return new Rect(rect.getX(), y, rect.getWidth(), height);
        }
        else {
            int width = (int) (rect.getWidth() * fill);
            int x = rect.getX() + (reverseFill ? rect.getWidth() - width : 0);
            return new Rect(x, rect.getY(), width, rect.getHeight());
        }
    }

    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        Size refSize = data.getRect().getSize();
        Size currSize = rect.getSize();

        Rect refDynamicRect = data.getDynamicRect();
        Rect refStaticRect = data.getStaticRect();

        if (expandMode.equals(UIExpandMode.FILL)) {
            Size newDynamicSize = Size.updateSize(refDynamicRect.getSize(), refSize, currSize);
            dynamicRect.setSize(newDynamicSize);

            Size newStaticSize = Size.updateSize(refStaticRect.getSize(), refSize, currSize);
            staticRect.setSize(newStaticSize);
        }

        onComponentChanged();
    }

    public enum Type {
        HORIZONTAL,
        VERTICAL
    }
}
