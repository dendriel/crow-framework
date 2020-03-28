package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.api.UIComponent;
import com.rozsa.crow.screen.ui.api.UIComponentObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseView extends JPanel implements UIComponentObserver {
    protected final List<UIComponent> components;
    protected final Rect rect;

    public BaseView(Rect rect) {
        this.rect = rect;
        components = new ArrayList<>();

        setup();
    }

    private void setup() {
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setBackground(Color.red);
        setOpaque(false);
        setLayout(null);
    }

    protected void addComponent(UIComponent component) {
        component.addUIComponentChangedListener(this);
        component.wrapUp(this);
        components.add(component);
    }

    private void clearComponent(UIComponent component) {
        component.removeUIComponentChangedListener(this);
        component.destroy(this);
    }

    protected void clearComponents() {
        components.forEach(this::clearComponent);
        components.clear();
        draw();
    }

    public void showAllComponents() {
        components.forEach(UIComponent::show);
    }

    public void hideAllComponents() {
        components.forEach(UIComponent::hide);
    }

    public void draw() {
        repaint();
    }

    @Override
    public void componentChanged() {
        draw();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        components.forEach(c -> c.paint(g, this));
    }
}
