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
    private Offset parentOffset;
    private Size expandFactor;
    private Offset customOffset;

    public UILabel(UILabelTemplate data) {
        this(data, new Offset(), new Size());
    }

    public UILabel(UILabelTemplate data, Offset parentOffset) {
        this(data, parentOffset, new Size());
    }

    public UILabel(UILabelTemplate data, Size expandFactor) {
        this(data, new Offset(), expandFactor);
    }

    public UILabel(UILabelTemplate data, Offset parentOffset, Size expandFactor) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;
        this.expandFactor = expandFactor;
        customOffset = Offset.origin();
        setup();
    }

    private void setup() {
        label = new JLabel();
        setupLabel();
    }

    public void updateComponentTemplate(UILabelTemplate data) {
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setupLabel();
    }

    public void setExpandFactor(Size expandFactor) {
        this.expandFactor = expandFactor;
        setupLabel();
    }

    private void setupLabel() {
        Font font = data.getFont().getJFont();
        label.setFont(font);

        Color color = data.getColor();
        label.setForeground(color);

        resetAlignment();
        label.setText(data.getValue());

        updateBounds();
    }

    private void updateBounds() {
        Rect rect = data.getRect();
        Rect bounds = rect.clone();
        bounds.setX(bounds.getX() + customOffset.getX() + parentOffset.getX() + expandFactor.getWidth() / 2);
        bounds.setY(bounds.getY() + customOffset.getY() + parentOffset.getY() + expandFactor.getHeight() / 2);
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
        updateBounds();
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
