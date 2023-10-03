package com.rozsa.screen.ui;

import com.rozsa.shared.attributes.Offset;
import com.rozsa.shared.attributes.Size;
import com.rozsa.screen.ui.api.UIComponentObserver;
import com.rozsa.screen.ui.api.UIHandler;
import com.rozsa.screen.ui.api.UISelectOption;
import com.rozsa.screen.ui.api.UISlotGroupHandler;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.List;

public class UISelect extends UIBaseComponent<UISlotGroupTemplate> implements UIHandler, UISlotGroupHandler {
    private final Offset parentOffset;
    private UISelectTemplate data;
    private UISlotGroup slots;
    private UILabelGroup labels;
    private SelectHandlerController handlerController;
    private HashSet<SelectOptionListener> optionListeners;
    private List<? extends UISelectOption> options;

    public UISelect(UISelectTemplate data) {
        this(data, Offset.origin());
    }

    public UISelect(UISelectTemplate data, Offset parentOffset) {
        super(data);
        this.parentOffset = parentOffset;
        this.data = data;

        optionListeners = new HashSet<>();

        setup();
    }

    private void setup() {
        slots = new UISlotGroup(data.getOptionsSlotGroup(), data.getPos().add(parentOffset));
        labels = new UILabelGroup(data.getLabelGroup());

        handlerController = new SelectHandlerController(this);
    }

    public void updateScreenSize(Size parentSize) {
        // TODO: updateScreenSize
    }

    public void registerListener(SelectOptionListener listener) {
        optionListeners.add(listener);
    }

    @Override
    public void wrapUp(Container container) {
        labels.wrapUp(container);
        slots.wrapUp(container);
    }

    @Override
    public void addUIComponentChangedListener(UIComponentObserver observer) {
        observers.add(observer);
        slots.addUIComponentChangedListener(observer);
        labels.addUIComponentChangedListener(observer);
    }

    @Override
    public void removeUIComponentChangedListener(UIComponentObserver observer) {
        observers.remove(observer);
        slots.removeUIComponentChangedListener(observer);
        labels.removeUIComponentChangedListener(observer);
    }

    public void moveHandlerUp() {
        handlerController.moveHandlerUp();
    }

    public void moveHandlerDown() {
        handlerController.moveHandlerDown();
    }

    public void select() {
        int currSelectOptionIndex = handlerController.getCurrentHandlerIndex();
        optionListeners.forEach(l -> l.onOptionSelected(currSelectOptionIndex));
    }

    public UISelectOption getOption(int index) {
        return options.get(index);
    }

    public void setupOptions(List<? extends UISelectOption> options) {
        setupOptions(options, new Offset());
    }

    public void setupOptions(List<? extends UISelectOption> options, Offset offset) {
        this.options = options;

        labels.clearAll();
        int currElementsCount = options.size();

        labels.set(options, offset);
        slots.setCustomOffset(offset);
        slots.show(currElementsCount);

        handlerController.setElementsCount(currElementsCount);
        handlerController.setHandlerDefaultPosition();
    }

    @Override
    public void updateComponentTemplate(UISlotGroupTemplate data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void show() {
        slots.show();
        labels.show();
        isEnabled = true;
    }

    @Override
    public void hide() {
        slots.hide();
        labels.hide();
        isEnabled = false;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        slots.paint(g, observer);
        labels.paint(g, observer);
    }

    @Override
    public void setHandlerEnabled(boolean enabled) {
        slots.setHandlerEnabled(enabled);
    }

    public void setHandlerEnabled(boolean enabled, int slotIndex) {
        slots.setHandlerEnabled(enabled, slotIndex);
    }

    @Override
    public void resetHandlers() {
        setHandlerEnabled(false);
    }

    @Override
    public UISlotGroup getSlotGroup() {
        return slots;
    }

    @FunctionalInterface
    public interface SelectOptionListener {
        void onOptionSelected(int optionIndex);
    }
}
