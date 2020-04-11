package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.*;
import com.rozsa.crow.screen.ui.button.UIButtonTemplate;
import com.rozsa.crow.screen.ui.input.UIInputFieldTemplate;

import java.util.ArrayList;
import java.util.List;

public class ViewTemplate {
    private Rect rect;
    private List<UIBaseComponentTemplate> components;
    private List<UILabelTemplate> labels;
    private List<UIIconTemplate> icons;
    private List<UIButtonTemplate> buttons;
    private List<UIInputFieldTemplate> inputFields;
    private List<UIAnimationTemplate> animations;

    public ViewTemplate() {
        labels = new ArrayList<>();
        icons = new ArrayList<>();
        buttons = new ArrayList<>();
        inputFields = new ArrayList<>();
        animations = new ArrayList<>();
    }

    public ViewTemplate(Rect rect) {
        this();
        this.rect = rect;
    }

    public Rect getRect() {
        return rect.clone();
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public List<UIBaseComponentTemplate> getComponents() {
        if (components == null) {
            components = new ArrayList<>();
            components.addAll(labels);
            components.addAll(icons);
            components.addAll(buttons);
            components.addAll(inputFields);
            components.addAll(animations);
        }

        return components;
    }

    public List<UILabelTemplate> getLabels() {
        return labels;
    }

    public void setLabels(List<UILabelTemplate> labels) {
        this.labels = labels;
    }

    public List<UIIconTemplate> getIcons() {
        return icons;
    }

    public void setIcons(List<UIIconTemplate> icons) {
        this.icons = icons;
    }

    public List<UIButtonTemplate> getButtons() {
        return buttons;
    }

    public void setButtons(List<UIButtonTemplate> buttons) {
        this.buttons = buttons;
    }

    public List<UIInputFieldTemplate> getInputFields() {
        return inputFields;
    }

    public void setInputFields(List<UIInputFieldTemplate> inputFields) {
        this.inputFields = inputFields;
    }

    public List<UIAnimationTemplate> getAnimations() {
        return animations;
    }

    public void setAnimations(List<UIAnimationTemplate> animations) {
        this.animations = animations;
    }
}