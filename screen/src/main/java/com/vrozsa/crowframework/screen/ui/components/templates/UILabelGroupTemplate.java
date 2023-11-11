package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;

public class UILabelGroupTemplate extends UIBaseComponentTemplate {
    private UILabelTemplate labelData;
    private Offset pos;
    private Size size;
    private Size spacing;

    public UILabelGroupTemplate() {
        super(UIComponentType.LABEL_GROUP);
        pos = Offset.origin();
        spacing = new Size();
    }

    public UILabelTemplate getLabelData() {
        return labelData;
    }

    public void setLabelData(UILabelTemplate labelData) {
        this.labelData = labelData;
    }

    public Offset getPos() {
        return pos;
    }

    public void setPos(Offset pos) {
        this.pos = pos;
    }

    public Size getSpacing() {
        return spacing;
    }

    public void setSpacing(Size spacing) {
        this.spacing = spacing;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getLabelCount() {
        return size.getWidth() * size.getHeight();
    }

    public UILabelTemplate copyLabelData() {
        return labelData.clone();
    }
}
