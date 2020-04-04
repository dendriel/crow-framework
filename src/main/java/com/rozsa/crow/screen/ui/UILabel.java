package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class UILabel extends UIBaseComponent<UILabelTemplate> {
    private UILabelTemplate data;
    private JLabel label;
    private Offset customOffset;

    public UILabel(UILabelTemplate data) {
        this(data, new Offset());
    }

    public UILabel(UILabelTemplate data, Offset parentOffset) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;
        customOffset = Offset.origin();
        setup();
    }

    private void setup() {
        label = new JLabel();
        rect = data.getRect();
        setupLabel();
    }

    public void updateComponentTemplate(UILabelTemplate data) {
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setupLabel();
    }

    private void setupLabel() {
        Font font = data.getFont().getJFont();
        label.setFont(font);

        Color color = data.getColor();
        label.setForeground(color);

        resetAlignment();
        label.setText(data.getValue());

        setupBounds();
    }

    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        if (expandMode.equals(UIExpandMode.FILL)) {
            Size refSize = data.getReferenceSize();
            UIFontTemplate font = UIFontTemplate.updateFontTemplate(data.getFont(), refSize.getHeight(), parentSize.getHeight());
            label.setFont(font.getJFont());
        }

        setupBounds();
    }

    private void setupBounds() {
        Rect bounds = rect.clone();
        bounds.setX(bounds.getX() + customOffset.getX() + parentOffset.getX());
        bounds.setY(bounds.getY() + customOffset.getY() + parentOffset.getY());
        label.setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void setVerticalAlignment(int alignment) {
        label.setVerticalAlignment(alignment);
    }

    public void setHorizontalAlignment(int alignment) {
        label.setHorizontalAlignment(alignment);
    }

    public void resetAlignment() {
        // CENTER = 0; TOP = 1; BOTTOM = 3.
        label.setVerticalAlignment(data.getVerticalAlignment());
        // CENTER = 0; LEFT = 2; RIGHT = 4.
        label.setHorizontalAlignment(data.getHorizontalAlignment());
    }

    public void setCustomOffset(Offset offset) {
        customOffset = offset;
        setupBounds();
    }

    public Rect getRect() {
        return data.getRect();
    }

    public void clear() {
        label.setText("");
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
    }

    public void show() {
        isEnabled = true;
        label.setVisible(true);
    }

    public void hide() {
        isEnabled = false;
        label.setVisible(false);
    }

    @Override
    public void wrapUp(Container container) {
        super.wrapUp(container);
        container.add(label);
    }

    @Override
    public void destroy(Container container) {
        super.destroy(container);
        container.remove(label);
    }
}
