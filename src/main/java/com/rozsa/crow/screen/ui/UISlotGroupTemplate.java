package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Size;

public class UISlotGroupTemplate {
    private UISlotTemplate slotData;
    private Size size;
    private Offset spacing;

    public UISlotGroupTemplate() {
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
