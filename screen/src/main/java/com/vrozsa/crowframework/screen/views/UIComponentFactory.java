package com.vrozsa.crowframework.screen.views;

import com.vrozsa.crowframework.screen.ui.components.UIAnimation;
import com.vrozsa.crowframework.screen.ui.components.templates.UIAnimationTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIBaseComponentTemplate;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.components.UILabel;
import com.vrozsa.crowframework.screen.ui.components.UILabelGroup;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelGroupTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.components.UIFillBar;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFillBarTemplate;
import com.vrozsa.crowframework.screen.ui.components.button.UIButton;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponent;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.button.UIButtonGroup;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonGroupTemplate;
import com.vrozsa.crowframework.screen.ui.components.input.UIInputField;
import com.vrozsa.crowframework.screen.ui.components.templates.UIInputFieldTemplate;
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
final class UIComponentFactory {
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
        typeToCreatorMapper.put(UIComponentType.FILL_BAR, template -> new UIFillBar((UIFillBarTemplate) template));
        typeToCreatorMapper.put(UIComponentType.BUTTON_GROUP, template -> new UIButtonGroup((UIButtonGroupTemplate) template));
    }

    static UIComponent<? extends UIBaseComponentTemplate> create(final UIBaseComponentTemplate template, final Size viewSize) {
        template.setReferenceSize(viewSize);
        return typeToCreatorMapper.get(template.getType()).apply(template);
    }
}
