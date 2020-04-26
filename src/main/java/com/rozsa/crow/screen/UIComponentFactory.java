package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.*;
import com.rozsa.crow.screen.ui.api.UIComponent;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.crow.screen.ui.button.UIButtonTemplate;
import com.rozsa.crow.screen.ui.input.UIInputField;
import com.rozsa.crow.screen.ui.input.UIInputFieldTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.rozsa.crow.screen.ui.UIComponentType.*;

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
    }

    static UIComponent create(UIBaseComponentTemplate template, Size viewSize) {
        template.setReferenceSize(viewSize);
        return typeToCreatorMapper.get(template.getType()).apply(template);
    }
}
