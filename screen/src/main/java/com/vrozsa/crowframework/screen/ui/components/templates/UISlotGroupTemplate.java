package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;

public final class UISlotGroupTemplate extends UIBaseComponentTemplate{
    private UISlotTemplate slotData;
    private Size size;
    private Offset spacing;

    public UISlotGroupTemplate() {
        super(UIComponentType.SLOT_GROUP);
        spacing = new Offset();
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setSlotData(UISlotTemplate slotData) {
        this.slotData = slotData;
    }

    public Offset getSpacing() {
        return spacing;
    }

    public void setSpacing(Offset spacing) {
        this.spacing = spacing;
    }

    public UISlotTemplate copySlotData() {
        UIIconTemplate bgImage = slotData.getBackgroundImage();
        if (bgImage != null) {
            slotData.setRect(bgImage.getRect().clone());
        }
        return slotData.clone();
    }
}
