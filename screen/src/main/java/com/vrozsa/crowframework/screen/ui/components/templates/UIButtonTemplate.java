package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;
import lombok.Data;

@Data
public final class UIButtonTemplate extends UIBaseComponentTemplate {
    private String defaultImage;
    private String pressedImage;
    private String rolloverImage;
    private String disabledImage;
    private boolean isDisabled;
    private boolean isFocusable;
    private UILabelTemplate label;
    private UIToolTipTemplate toolTip;

    public UIButtonTemplate() {
        super(UIComponentType.BUTTON);
//        toolTip = new UIToolTipTemplate();
    }

    @Builder
    public UIButtonTemplate(
            Rect rect,
            String tag,
            UIExpandMode expandMode,
            Size referenceSize,
            boolean isEnabled,
            String defaultImage,
            String pressedImage,
            String rolloverImage,
            String disabledImage,
            boolean isDisabled,
            boolean isFocusable,
            UILabelTemplate label,
            UIToolTipTemplate toolTip
    ) {
        super(UIComponentType.BUTTON, rect, tag, expandMode, referenceSize, isEnabled);
        this.defaultImage = defaultImage;
        this.pressedImage = pressedImage;
        this.rolloverImage = rolloverImage;
        this.disabledImage = disabledImage;
        this.isDisabled = isDisabled;
        this.isFocusable = isFocusable;
        this.label = label;
        this.toolTip = toolTip;
    }

    public static class UIButtonTemplateBuilder {
        private UIExpandMode expandMode = DEFAULT_EXPAND_MODE;
        private boolean isEnabled = DEFAULT_IS_ENABLED;
    }
}
