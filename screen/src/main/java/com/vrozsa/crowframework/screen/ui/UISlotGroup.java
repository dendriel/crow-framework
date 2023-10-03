package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.screen.ui.api.UIHandler;
import com.vrozsa.crowframework.screen.ui.api.UISlotContent;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UISlotGroup extends UIBaseComponent<UISlotGroupTemplate> implements UIHandler {
    private final List<UISlot> slots;
    private final UISlotGroupTemplate data;
    private final Offset parentOffset;

    public UISlotGroup(UISlotGroupTemplate data) {
        this(data, new Offset());
    }

    public UISlotGroup(UISlotGroupTemplate data, Offset parentOffset) {
        super(data);
        this.data = data;
        this.parentOffset = parentOffset;

        slots = new ArrayList<>();

        setup();
    }

    private void setup() {
        int size = getSlotCount();
        for (int i = 0; i < size; i++) {
            UISlot newSlot = createSlot(i);
            slots.add(newSlot);
        }
    }

    public int getSlotCount() {
        return data.getWidth() * data.getHeight();
    }

    public void set(UISlotContent content, int index) {
        set(content, index, true);
    }

    public void set(UISlotContent content, int index, boolean isContentVisible) {
        assert index < slots.size() : String.format("Trying to set an invalid slot index [%d]", index);

        UISlot slot = slots.get(index);
        slot.setContent(content, isContentVisible);
        onComponentChanged();
    }

    public void show(int count) {
        assert slots.size() >= count : String.format("Invalid count [%d] to show slots. Available [%d]", count, slots.size());

        slots.forEach(s -> s.isEnabled = false);
        for (int i = 0; i < count; i++) {
            slots.get(i).isEnabled = true;
        }
    }

    public void setCustomOffset(Offset offset) {
        slots.forEach(s -> s.setCustomOffset(offset));
    }

    public void refresh(int index) {
        UISlot slot = slots.get(index);
        slot.refreshCountLabel();
    }

    public void clear(int index) {
        slots.get(index).clear();
        onComponentChanged();
    }

    public void clearAll() {
        slots.forEach(UISlot::clear);
        onComponentChanged();
    }

    public void updateScreenSize(Size parentSize) {
        // TODO: updateScreenSize
    }


    private UISlot createSlot(int index) {
        UISlotTemplate newData = data.copySlotData();
        Offset spacing = data.getSpacing();

        Rect rect = newData.getRect();
        int y = index / data.getWidth();
        int x = index % data.getWidth();

        int slotX = rect.getX() + x * (rect.getWidth() + spacing.getX());
        int slotY = rect.getY() + y * (rect.getHeight() + spacing.getY());

        rect.setX(slotX);
        rect.setY(slotY);

        return new UISlot(newData, parentOffset);
    }

    @Override
    public void updateComponentTemplate(UISlotGroupTemplate data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void show() {
        isEnabled = true;
    }

    @Override
    public void hide() {
        isEnabled = false;
    }

    public void showAllSlotsBackgrounds() {
        slots.forEach(s -> s.setBackgroundVisible(true));
    }

    public void hideAllHandlers() {
        slots.forEach(s -> s.setHandlerEnabled(false));
    }

    @Override
    public void wrapUp(Container container) {
        slots.forEach(s -> s.wrapUp(container));
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;

        List<UISlot> enabledSlots = slots.stream().filter(s -> s.isEnabled).collect(Collectors.toList());
        enabledSlots.forEach(s -> s.paint(g, observer));
    }

    @Override
    public void setHandlerEnabled(boolean enabled) {
        slots.forEach(c -> c.setHandlerEnabled(enabled));
        onComponentChanged();
    }

    public void setHandlerEnabled(boolean enabled, int slotIndex) {
        assert slotIndex < slots.size() : String.format("Invalid slot index [%d]. Slot length is [%d].", slotIndex, slots.size());
        slots.get(slotIndex).setHandlerEnabled(enabled);
        onComponentChanged();
    }

    public UISlot getSlotAt(int slotIndex) {
        assert slotIndex < slots.size() : String.format("Invalid slot index [%d]. Slot length is [%d].", slotIndex, slots.size());
        return slots.get(slotIndex);
    }

    public int getWidth() {
        return data.getWidth();
    }
}
