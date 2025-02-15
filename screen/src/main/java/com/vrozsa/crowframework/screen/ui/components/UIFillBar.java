package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFillBarTemplate;
import com.vrozsa.crowframework.shared.image.ImageLoader;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.screen.ui.components.api.UIFiller;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public final class UIFillBar extends AbstractUIComponent<UIFillBarTemplate> implements UIFiller {
    private final UIFillBarTemplate data;
    private final Image dynamicImage;
    private final Image staticImage;
    private final Type fillBarType;
    private final boolean reverseFill;

    private Rect staticRect;
    private Rect dynamicRect;
    private float fill;

    public UIFillBar(UIFillBarTemplate data) {
        super(data);
        this.data = data;
        fillBarType = data.getFillBarType();
        reverseFill = data.isReverseFill();
        fill = data.getFill();

        staticImage = ImageLoader.load(data.getStaticImage());
        dynamicImage = ImageLoader.load(data.getDynamicImage());

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
        if (!isEnabled) {
            return;
        }

        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();
        var newDynamicRect = getBackgroundRect(dynamicRect);

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
    public void updateComponentTemplate(UIFillBarTemplate data) {
        throw new UnsupportedOperationException();
    }

    private Rect getBackgroundRect(Rect rect) {
        if (fillBarType == Type.VERTICAL) {
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

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        var refSize = data.getRect().getSize();
        var currSize = rect.getSize();

        var refDynamicRect = data.getDynamicRect();
        var refStaticRect = data.getStaticRect();

        if (expandMode == UIExpandMode.FILL) {
            var newDynamicSize = Size.updateSize(refDynamicRect.getSize(), refSize, currSize);
            dynamicRect.setSize(newDynamicSize);

            var newStaticSize = Size.updateSize(refStaticRect.getSize(), refSize, currSize);
            staticRect.setSize(newStaticSize);
        }

        onComponentChanged();
    }

    /**
     * Type the fill-bar.
     */
    public enum Type {
        /**
         * Horizontally oriented  fill-bar.
         */
        HORIZONTAL,
        /**
         * Vertically oriented  fill-bar.
         */
        VERTICAL
    }
}
