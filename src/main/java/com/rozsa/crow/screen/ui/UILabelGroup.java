package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.api.UIText;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class UILabelGroup extends UIBaseComponent<UILabelGroupTemplate> {
    private final List<UILabel> labels;

    private final UILabelGroupTemplate data;

    private final Offset parentOffset;

    public UILabelGroup(UILabelGroupTemplate data) {
        this(data, new Offset());
    }

    public UILabelGroup(UILabelGroupTemplate data, Offset parentOffset) {
        this.data = data;
        this.parentOffset = parentOffset;

        labels = new ArrayList<>();

        setup();
    }

    private void setup() {
        int size = getLabelCount();
        for (int i = 0; i < size; i++) {
            UILabel newLabel = createLabel(i);
            labels.add(newLabel);
        }
    }

    public int getLabelCount() {
        return data.getLabelCount();
    }

    public void set(List<? extends UIText> texts) {
        set(texts, Offset.origin());
    }

    public void set(List<? extends UIText> texts, Offset offset) {
        for (int i = 0; i < texts.size(); i++) {
            UIText text = texts.get(i);
            set(text, i, offset);
        }
    }

    public void set(UIText content, int index, Offset offset) {
        assert index < labels.size() : String.format("Trying to set an invalid label index [%d]", index);

        UILabel label = labels.get(index);

        label.setText(content.getValue());
        label.setCustomOffset(offset);
        onComponentChanged();
    }

    public void clear(int index) {
        labels.get(index).clear();
        onComponentChanged();
    }

    public void clearAll() {
        labels.forEach(UILabel::clear);
        onComponentChanged();
    }

    private UILabel createLabel(int index) {
        UILabelTemplate newData = data.copyLabelData();
        Offset spacing = data.getSpacing();
        Offset pos = data.getPos();

        Rect rect = newData.getRect();
        int x = pos.getX() + (index * spacing.getX());
        int y = pos.getY() + index * (spacing.getY() + rect.getHeight());

        rect.setX(x);
        rect.setY(y);

        return new UILabel(newData, parentOffset);
    }

    @Override
    public void show() {
        isEnabled = true;
        labels.forEach(UILabel::show);
    }

    @Override
    public void hide() {
        isEnabled = false;
        labels.forEach(UILabel::hide);
    }

    @Override
    public void wrapUp(Container container) {
        labels.forEach(s -> s.wrapUp(container));
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;
        labels.forEach(s -> s.paint(g, observer));
    }

    @Override
    public void updateComponentTemplate(UILabelGroupTemplate data) {
        throw new NotImplementedException();
    }
}
