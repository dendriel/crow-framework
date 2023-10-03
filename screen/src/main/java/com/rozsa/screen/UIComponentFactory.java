package com.rozsa.screen;

import com.rozsa.shared.attributes.Size;
import com.rozsa.screen.ui.*;
import com.rozsa.screen.ui.api.UIComponent;
import com.rozsa.screen.ui.button.UIButton;
import com.rozsa.screen.ui.button.UIButtonTemplate;
import com.rozsa.screen.ui.buttongroup.UIButtonGroup;
import com.rozsa.screen.ui.buttongroup.UIButtonGroupTemplate;
import com.rozsa.screen.ui.input.UIInputField;
import com.rozsa.screen.ui.input.UIInputFieldTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.rozsa.screen.ui.UIComponentType.*;

class UIComponentFactory {
    private static Map<UIComponentType, Function<UIBaseComponentTemplate, UIComponent>> typeToCreatorMapper;

    static {
        typeToCreatorMapper = new HashMap<>();
        typeToCreatorMapper.put(LABEL, template -> new UILabel((UILabelTemplate) template));
        typeToCreatorMapper.put(ICON, template -> new UIIcon((UIIconTemplate) template));
        typeToCreatorMapper.put(INPUT_FIELD, template -> new UIInputField((UIInputFieldTemplate) template));
        typeToCreatorMapper.put(BUTTON, template -> new UIButton((UIButtonTemplate) template));
        typeToCreatorMapper.put(ANIMATION, template -> new UIAnimation((UIAnimationTemplate) template));
        typeToCreatorMapper.put(LABEL_GROUP, template -> new UILabelGroup((UILabelGroupTemplate) template));
        typeToCreatorMapper.put(SLIDER, template -> new UISlider((UISliderTemplate) template));
        typeToCreatorMapper.put(BUTTON_GROUP, template -> new UIButtonGroup((UIButtonGroupTemplate) template));
    }

    static UIComponent create(UIBaseComponentTemplate template, Size viewSize) {
        template.setReferenceSize(viewSize);
        return typeToCreatorMapper.get(template.getType()).apply(template);
    }
}
