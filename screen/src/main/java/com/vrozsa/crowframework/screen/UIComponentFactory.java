package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.screen.ui.UIAnimation;
import com.vrozsa.crowframework.screen.ui.UIAnimationTemplate;
import com.vrozsa.crowframework.screen.ui.UIBaseComponentTemplate;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
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
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponent;
import com.vrozsa.crowframework.screen.ui.button.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroup;
import com.vrozsa.crowframework.screen.ui.buttongroup.UIButtonGroupTemplate;
import com.vrozsa.crowframework.screen.ui.input.UIInputField;
import com.vrozsa.crowframework.screen.ui.input.UIInputFieldTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Creates UIComponents from data templates.
 * <p>
 *     Based on Dynamic Factory Pattern.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UIComponentFactory {
    private static final
        Map<UIComponentType, Function<UIBaseComponentTemplate, UIComponent<? extends UIBaseComponentTemplate>>> typeToCreatorMapper;

    static {
        typeToCreatorMapper = new EnumMap<>(UIComponentType.class);
        typeToCreatorMapper.put(UIComponentType.LABEL, template -> UILabel.from((UILabelTemplate) template));
        typeToCreatorMapper.put(UIComponentType.ICON, template -> UIIcon.from((UIIconTemplate) template));
        typeToCreatorMapper.put(UIComponentType.INPUT_FIELD, template -> new UIInputField((UIInputFieldTemplate) template));
        typeToCreatorMapper.put(UIComponentType.BUTTON, template -> new UIButton((UIButtonTemplate) template));
        typeToCreatorMapper.put(UIComponentType.ANIMATION, template -> new UIAnimation((UIAnimationTemplate) template));
        typeToCreatorMapper.put(UIComponentType.LABEL_GROUP, template -> new UILabelGroup((UILabelGroupTemplate) template));
        typeToCreatorMapper.put(UIComponentType.SLIDER, template -> new UISlider((UISliderTemplate) template));
        typeToCreatorMapper.put(UIComponentType.BUTTON_GROUP, template -> new UIButtonGroup((UIButtonGroupTemplate) template));
    }

    static UIComponent<? extends UIBaseComponentTemplate> create(final UIBaseComponentTemplate template, final Size viewSize) {
        template.setReferenceSize(viewSize);
        return typeToCreatorMapper.get(template.getType()).apply(template);
    }
}
