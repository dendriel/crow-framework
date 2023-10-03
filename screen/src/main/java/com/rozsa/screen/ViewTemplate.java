package com.rozsa.screen;

import com.rozsa.shared.attributes.Rect;
import com.rozsa.screen.ui.*;
import com.rozsa.screen.ui.button.UIButtonTemplate;
import com.rozsa.screen.ui.buttongroup.UIButtonGroupTemplate;
import com.rozsa.screen.ui.input.UIInputFieldTemplate;

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
    private List<UILabelGroupTemplate> labelGroups;
    private List<UISliderTemplate> sliders;
    private List<UIButtonGroupTemplate> buttonGroups;

    public ViewTemplate() {
        labels = new ArrayList<>();
        icons = new ArrayList<>();
        buttons = new ArrayList<>();
        inputFields = new ArrayList<>();
        animations = new ArrayList<>();
        labelGroups = new ArrayList<>();
        sliders = new ArrayList<>();
        buttonGroups = new ArrayList<>();
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
            components.addAll(labelGroups);
            components.addAll(sliders);
            components.addAll(buttonGroups);
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

    public List<UILabelGroupTemplate> getLabelGroups() {
        return labelGroups;
    }

    public void setLabelGroups(List<UILabelGroupTemplate> labelGroups) {
        this.labelGroups = labelGroups;
    }

    public List<UISliderTemplate> getSliders() {
        return sliders;
    }

    public void setSliders(List<UISliderTemplate> sliders) {
        this.sliders = sliders;
    }

    public List<UIButtonGroupTemplate> getButtonGroups() {
        return buttonGroups;
    }

    public void setButtonGroups(List<UIButtonGroupTemplate> buttonGroups) {
        this.buttonGroups = buttonGroups;
    }
}
