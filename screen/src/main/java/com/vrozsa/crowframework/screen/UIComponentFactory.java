package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.screen.ui.UIAnimation;
import com.vrozsa.crowframework.screen.ui.UIAnimationTemplate;
import com.vrozsa.crowframework.screen.ui.UIBaseComponentTemplate;
import com.vrozsa.crowframework.screen.ui.UIComponentType;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.UILabel;
import com.vrozsa.crowframework.screen.ui.UILabelGroup;
import com.vrozsa.crowframework.screen.ui.UILabelGroupTemplate;
import com.vrozsa.crowframework.screen.ui.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.UISlider;
import com.vrozsa.crowframework.screen.ui.UISliderTemplate;
import com.vrozsa.crowframework.screen.ui.button.UIButton;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.screen.ui.api.UIComponent;
import com.vrozsa.crowframework.screen.ui.button.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroup;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroupTemplate;
import com.vrozsa.crowframework.screen.ui.input.UIInputField;
import com.vrozsa.crowframework.screen.ui.input.UIInputFieldTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class UIComponentFactory {
    private static Map<UIComponentType, Function<UIBaseComponentTemplate, UIComponent>> typeToCreatorMapper;

    static {
        typeToCreatorMapper = new HashMap<>();
        typeToCreatorMapper.put(UIComponentType.LABEL, template -> new UILabel((UILabelTemplate) template));
        typeToCreatorMapper.put(UIComponentType.ICON, template -> new UIIcon((UIIconTemplate) template));
        typeToCreatorMapper.put(UIComponentType.INPUT_FIELD, template -> new UIInputField((UIInputFieldTemplate) template));
        typeToCreatorMapper.put(UIComponentType.BUTTON, template -> new UIButton((UIButtonTemplate) template));
        typeToCreatorMapper.put(UIComponentType.ANIMATION, template -> new UIAnimation((UIAnimationTemplate) template));
        typeToCreatorMapper.put(UIComponentType.LABEL_GROUP, template -> new UILabelGroup((UILabelGroupTemplate) template));
        typeToCreatorMapper.put(UIComponentType.SLIDER, template -> new UISlider((UISliderTemplate) template));
        typeToCreatorMapper.put(UIComponentType.BUTTON_GROUP, template -> new UIButtonGroup((UIButtonGroupTemplate) template));
    }

    static UIComponent create(UIBaseComponentTemplate template, Size viewSize) {
        template.setReferenceSize(viewSize);
        return typeToCreatorMapper.get(template.getType()).apply(template);
    }
}
