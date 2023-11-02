package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.screen.ui.UIAnimationTemplate;
import com.vrozsa.crowframework.screen.ui.UIBaseComponentTemplate;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.UILabelGroupTemplate;
import com.vrozsa.crowframework.screen.ui.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.UISliderTemplate;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.screen.ui.button.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroupTemplate;
import com.vrozsa.crowframework.screen.ui.input.UIInputFieldTemplate;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// TODO: turn in a record
@Data
public class UIViewTemplate {
    private String name;
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

    public UIViewTemplate() {
        labels = new ArrayList<>();
        icons = new ArrayList<>();
        buttons = new ArrayList<>();
        inputFields = new ArrayList<>();
        animations = new ArrayList<>();
        labelGroups = new ArrayList<>();
        sliders = new ArrayList<>();
        buttonGroups = new ArrayList<>();
    }

    public UIViewTemplate(final String name, final Rect rect) {
        this();
        this.name = name;
        this.rect = rect;
    }

    public Rect getRect() {
        return rect.clone();
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
}
