package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public final class UILabel extends AbstractUIComponent<UILabelTemplate> {
    private UILabelTemplate data;
    private JLabel label;
    private Offset customOffset;

    private UILabel(UILabelTemplate data) {
        this(data, Offset.origin());
    }

    private UILabel(UILabelTemplate data, Offset parentOffset) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;
        customOffset = Offset.origin();
        setup();
    }

    public static UILabel from(final UILabelTemplate data) {
        return new UILabel(data);
    }

    public static UILabel from(final UILabelTemplate data, final Offset parentOffset) {
        return new UILabel(data, parentOffset);
    }

    private void setup() {
        label = new JLabel();
        rect = data.getRect();
        setupLabel();
    }

    public UILabelTemplate getTemplate() {
        return data;
    }

    public void updateComponentTemplate(UILabelTemplate data) {
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setupLabel();
    }

    private void setupLabel() {
        var font = data.getFont().getJFont();
        label.setFont(font);

        var color = data.getColor();
        label.setForeground(color);

        resetAlignment();
        label.setText(data.getValue());

        setupBounds();

        label.setVisible(isEnabled);
    }

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        if (UIExpandMode.FILL == expandMode) {
            var refSize = data.getReferenceSize();
            var fontTemplate = UIFontTemplate.updateFontTemplate(data.getFont(), refSize.getHeight(), parentSize.getHeight());
            label.setFont(fontTemplate.getJFont());
        }

        setupBounds();
    }

    private void setupBounds() {
        var bounds = rect.clone();
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

    public Offset getCustomOffset() {
        return customOffset;
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
