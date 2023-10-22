package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

public class UIIconTemplate extends UIBaseComponentTemplate {
    private String imageFile;

    public UIIconTemplate() {
        super(UIComponentType.ICON);
    }

    protected UIIconTemplate(UIComponentType type) {
        super(type);
    }

    @Builder
    public UIIconTemplate(
            String imageFile,
            UIComponentType type,
            Rect rect,
            String tag,
            UIExpandMode expandMode,
            Size referenceSize,
            boolean isEnabled
    ) {
        super(type, rect, tag, expandMode, referenceSize, isEnabled);
        this.imageFile = imageFile;
    }

    public static class UIIconTemplateBuilder {
        private UIExpandMode expandMode = DEFAULT_EXPAND_MODE;
        private boolean isEnabled = DEFAULT_IS_ENABLED;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        UIIconTemplate other = (UIIconTemplate) obj;
        if (other == null) {
            return false;
        }

        boolean isEqual = imageFile.equals(other.getImageFile());
        isEqual &= rect.equals(other.getRect());

        return isEqual;
    }
}
