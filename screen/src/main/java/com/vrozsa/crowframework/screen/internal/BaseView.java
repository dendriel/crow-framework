package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.screen.ui.UIAnimation;
import com.vrozsa.crowframework.screen.ui.UIBaseComponentTemplate;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UILabel;
import com.vrozsa.crowframework.screen.ui.UILabelGroup;
import com.vrozsa.crowframework.screen.ui.UISlider;
import com.vrozsa.crowframework.screen.ui.button.UIButton;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroup;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponent;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentObserver;
import com.vrozsa.crowframework.screen.ui.input.UIInputField;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Views are the frame where components are displayed. A view is contained in the screen.
 */
public class BaseView extends JPanel implements View, UIComponentObserver {
    private final ViewTemplate data;
    protected final List<UIComponent<?>> components;
    protected final Rect rect;

    public BaseView(final String name, final Rect rect) {
        this(new ViewTemplate(name, rect));
    }

    public BaseView(final ViewTemplate data) {
        this.data = data;
        this.rect = data.getRect().clone();
        components = new ArrayList<>();

        setup();
    }

    private void setup() {
        setupBounds();
        setBackground(Color.red);
        setOpaque(false);
        setLayout(null);

        setupComponents();
    }

    private void setupBounds() {
        setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    private void setupComponents() {
        for (UIBaseComponentTemplate template : data.getComponents()) {
            UIComponent component = UIComponentFactory.create(template, rect.getSize());
            addComponent(component);
        }
    }

    public String name() {
        return data.getName();
    }

    public <T> void addComponent(final UIComponent<T> component) {
        component.addUIComponentChangedListener(this);
        component.wrapUp(this);
        components.add(component);
    }

    private <T> void clearComponent(UIComponent<T> component) {
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

    public void resize(final Size parentSize) {
        var rect = data.getRect();
        var refSize = rect.getSize();

        var newOffset = Offset.updateOffset(rect.getOffset(), refSize, parentSize);
        this.rect.setX(newOffset.getX());
        this.rect.setY(newOffset.getY());

        var newSize = Size.updateSize(rect.getSize(), refSize, parentSize);
        this.rect.setWidth(newSize.getWidth());
        this.rect.setHeight(newSize.getHeight());

        setupBounds();

        components.forEach(c -> c.updateScreenSize(parentSize));
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

    public UIAnimation getAnimation(String tag) {
        return getComponent(tag, UIAnimation.class);
    }

    public UILabelGroup getLabelGroup(String tag) {
        return getComponent(tag, UILabelGroup.class);
    }

    public UISlider getSlider(String tag) {
        return getComponent(tag, UISlider.class);
    }

    public UIButtonGroup getButtonGroup(String tag) {
        return getComponent(tag, UIButtonGroup.class);
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
