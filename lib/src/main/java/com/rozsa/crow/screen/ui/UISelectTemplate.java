package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;

public class UISelectTemplate extends UIBaseComponentTemplate {
    private Offset pos;
    private UISlotGroupTemplate optionsSlotGroup;
    private UILabelGroupTemplate labelGroup;

    public UISelectTemplate() {
        super(UIComponentType.SELECT);
    }

    public UISlotGroupTemplate getOptionsSlotGroup() {
        return optionsSlotGroup;
    }

    public void setOptionsSlotGroup(UISlotGroupTemplate optionsSlotGroup) {
        this.optionsSlotGroup = optionsSlotGroup;
    }

    public Offset getPos() {
        return pos;
    }

    public void setPos(Offset pos) {
        this.pos = pos;
    }

    public UILabelGroupTemplate getLabelGroup() {
        return labelGroup;
    }

    public void setLabelGroup(UILabelGroupTemplate labelGroup) {
        this.labelGroup = labelGroup;
    }
}
