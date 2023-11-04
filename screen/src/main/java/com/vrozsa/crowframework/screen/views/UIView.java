package com.vrozsa.crowframework.screen.views;

import com.vrozsa.crowframework.screen.ui.components.UIAnimation;
import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.UILabel;
import com.vrozsa.crowframework.screen.ui.components.UILabelGroup;
import com.vrozsa.crowframework.screen.ui.components.UISlider;
import com.vrozsa.crowframework.screen.ui.components.button.UIButton;
import com.vrozsa.crowframework.screen.ui.components.button.UIButtonGroup;
import com.vrozsa.crowframework.screen.ui.components.input.UIInputField;
import com.vrozsa.crowframework.screen.ui.components.templates.UIBaseComponentTemplate;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponent;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentObserver;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base view for rendering UI components.
 */
public class UIView extends AbstractView implements UIComponentObserver {
    /**
     * Default UI View name if no name is specified.
     */
    public static final String DEFAULT_UI_VIEW = "_defaultUiView";

    private final List<UIComponent<?>> components;

    private final UIViewTemplate template;

    /**
     * @param rect view rect in the screen.
     */
    public UIView(Rect rect) {
        this(DEFAULT_UI_VIEW, rect);
    }

    /**
     * @param name view name so it can be referenced while accessing it from the screen.
     * @param rect view rect in the screen.
     */
    public UIView(String name, Rect rect) {
        this(new UIViewTemplate(name, rect));
    }

    /**
     * @param template create the view from a template.
     */
    public UIView(final UIViewTemplate template) {
        super(template.getName(), template.getRect());
        components = new ArrayList<>();
        this.template = template;

        setupComponents();
    }

    private void setupComponents() {
        for (var componentTemplate : template.getComponents()) {
            var component = UIComponentFactory.create(componentTemplate, rect.getSize());
            addComponent(component);
        }
    }

    /**
     * Add a UI component into the view.
     * <p>
     *     Use this method only if you know what you are doing.
     *     Otherwise, you should be using: {@link #createComponent(UIBaseComponentTemplate)} instead.
     * </p>
     * @param component component to be added.
     * @param <T> component template type.
     */
    public <T> void addComponent(final UIComponent<T> component) {
        component.addUIComponentChangedListener(this);
        component.wrapUp(this);
        components.add(component);
    }

    /**
     * Creates a new component from its template.
     * <p>
     *     This is the default way to add new components in the view.
     * </p>
     * @param template the component template.
     * @return the created component.
     */
    public UIComponent<? extends UIBaseComponentTemplate> createComponent(final UIBaseComponentTemplate template) {
        var component = UIComponentFactory.create(template, rect.getSize());
        addComponent(component);
        return component;
    }

    /**
     * Removes a component into the view.
     * @param component component to be removed.
     * @param <T> component type.
     */
    public <T> void removeComponent(UIComponent<T> component) {
        component.removeUIComponentChangedListener(this);
        component.destroy(this);
    }

    public void clearComponents() {
        components.forEach(this::removeComponent);
        components.clear();
        draw();
    }

    public void showAllComponents() {
        components.forEach(UIComponent::show);
    }

    public void hideAllComponents() {
        components.forEach(UIComponent::hide);
    }

    @Override
    public void resize(Size parentSize) {
        super.resize(parentSize);
        components.forEach(c -> c.updateScreenSize(parentSize));
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

    // TODO: check if we are going to need all of these methods.
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
}
