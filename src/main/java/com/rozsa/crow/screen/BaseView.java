package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIBaseComponent;
import com.rozsa.crow.screen.ui.UIBaseComponentTemplate;
import com.rozsa.crow.screen.ui.UIIcon;
import com.rozsa.crow.screen.ui.UILabel;
import com.rozsa.crow.screen.ui.api.UIComponent;
import com.rozsa.crow.screen.ui.api.UIComponentObserver;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.crow.screen.ui.input.UIInputField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BaseView extends JPanel implements UIComponentObserver {
    private final ViewTemplate data;
    protected final List<UIComponent> components;
    protected final Rect rect;

    public BaseView(Rect rect) {
        this(new ViewTemplate(rect));
    }

    public BaseView(ViewTemplate data) {
        this.data = data;
        this.rect = data.getRect().clone();
        components = new ArrayList<>();

        setup();
    }

    private void setup() {
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        setBackground(Color.red);
        setOpaque(false);
        setLayout(null);

        setupComponents();
    }

    private void setupComponents() {
        for (UIBaseComponentTemplate template : data.getComponents()) {
            UIComponent component = UIComponentFactory.create(template);
            addComponent(component);
        }
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

    public UIButton getButton(String tag) {
        return getComponent(tag, UIButton.class);
    }

    public UIIcon getIcon(String tag) {
        return getComponent(tag, UIIcon.class);
    }

    public UILabel getLabel(String tag) {
        return getComponent(tag, UILabel.class);
    }

    public UIInputField getInputField(String tag) {
        return getComponent(tag, UIInputField.class);
    }

    public <T extends UIComponent> T getComponent(String tag, Class<T> clazz) {
        return clazz.cast(components
                .stream()
                .filter(c -> c.getTag().equals(tag))
                .findFirst()
                .orElse(null));
    }

    public <T extends UIComponent> List<T> getComponents(String tag, Class<T> clazz) {
        return components
                .stream()
                .filter(c -> c.getTag().equals(tag))
                .map(clazz::cast)
                .collect(Collectors.toList());
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
