package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Size;

public class UILabelGroupTemplate {
    private UILabelTemplate labelData;
    private Offset pos;
    private Size size;
    private Offset spacing;

    public UILabelGroupTemplate() {
        pos = new Offset();
        spacing = new Offset();
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

    public Offset getSpacing() {
        return spacing;
    }

    public void setSpacing(Offset spacing) {
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
