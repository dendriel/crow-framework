package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.components.api.UIText;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelGroupTemplate;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Label Group allows laying and handling labels as a group, auto spacing the labels and allowing individual access by
 * index.
 */
public class UILabelGroup extends AbstractUIComponent<UILabelGroupTemplate> {
    private final List<UILabel> labels;
    private final UILabelGroupTemplate data;

    public UILabelGroup(UILabelGroupTemplate data) {
        this(data, Offset.origin());
    }

    public UILabelGroup(UILabelGroupTemplate data, Offset parentOffset) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;

        labels = new ArrayList<>();

        setup();
    }

    private void setup() {
        data.getLabelData().setReferenceSize(data.getReferenceSize());
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
        var labelTemplate = data.copyLabelData();
        var spacing = data.getSpacing();
        var pos = data.getPos();

        var rect = labelTemplate.getRect();
        int horSpacing = (data.getSize().getWidth() > 1) ? spacing.getWidth() + rect.getWidth() : 0;
        int verSpacing = (data.getSize().getHeight() > 1) ? spacing.getHeight() + rect.getHeight() : 0;

        int x = pos.getX() + (index * horSpacing);
        int y = pos.getY() + (index * verSpacing);

        rect.setX(x);
        rect.setY(y);

        labelTemplate.setRect(rect);

        return UILabel.from(labelTemplate);
    }

    public void updateScreenSize(Size parentSize) {
        labels.forEach(l -> l.updateScreenSize(parentSize));
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
        throw new UnsupportedOperationException();
    }
}
