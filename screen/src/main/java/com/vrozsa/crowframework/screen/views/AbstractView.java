package com.vrozsa.crowframework.screen.views;

import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import javax.swing.JPanel;
import java.awt.Color;

/**
 * Views are the frame where components are displayed.
 */
public abstract class AbstractView extends JPanel implements View {
    protected final String name;
    protected final Rect baseRect;
    protected final Rect rect;

    protected AbstractView(final String name, final Rect rect) {
        this.name = name;
        this.baseRect = rect.clone();
        this.rect = rect.clone();

        setup();
    }

    private void setup() {
        setupBounds();
        setBackground(Color.red);
        setOpaque(false);
        setLayout(null);
    }

    private void setupBounds() {
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public String getName() {
        return name;
    }

    public void resize(final Size parentSize) {
        var refSize = baseRect.getSize();

        var newOffset = Offset.updateOffset(baseRect.getOffset(), refSize, parentSize);
        this.rect.setX(newOffset.getX());
        this.rect.setY(newOffset.getY());

        var newSize = Size.updateSize(baseRect.getSize(), refSize, parentSize);
        this.rect.setWidth(newSize.getWidth());
        this.rect.setHeight(newSize.getHeight());

        setupBounds();
    }

    public void draw() {
        repaint();
    }
}
