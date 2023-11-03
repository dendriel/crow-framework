package com.vrozsa.crowframework.screen.ui.controllers;

import com.vrozsa.crowframework.screen.ui.components.api.UISlotGroupHandler;
import com.vrozsa.crowframework.screen.ui.components.UISlot;
import com.vrozsa.crowframework.screen.ui.components.UISlotGroup;

public class BaseSlotGroupHandlerController {
    protected final UISlotGroupHandler slotGroupHandler;
    protected final UISlotGroup slotGroup;
    protected final ElementDisplayHandler elementDisplayHandler;
    protected int currHandlerIndex;
    protected int elementsCount;

    public BaseSlotGroupHandlerController(UISlotGroupHandler slotGroupHandler, int currMaxItemsSize) {
        this(slotGroupHandler, null, currMaxItemsSize);
    }

    public BaseSlotGroupHandlerController(
            UISlotGroupHandler slotGroupHandler, ElementDisplayHandler elementDisplayHandler, int currSlotsCount) {
        this.slotGroupHandler = slotGroupHandler;
        this.slotGroup = slotGroupHandler.getSlotGroup();
        this.elementsCount = currSlotsCount;
        this.elementDisplayHandler = elementDisplayHandler;

        currHandlerIndex = 0;
    }

    public void setElementsCount(int value) {
        elementsCount = value;
    }

    public void setHandlerDefaultPosition() {
        currHandlerIndex = 0;
        updateHandler();
    }

    protected void updateHandler() {
        slotGroupHandler.resetHandlers();
        slotGroup.setHandlerEnabled(false);
        slotGroup.setHandlerEnabled(true, currHandlerIndex);
        displayItemAtHandler();
    }

    public int getCurrentHandlerIndex() {
        return currHandlerIndex;
    }

    public UISlot getSlotAtHandler() {
        return slotGroup.getSlotAt(currHandlerIndex);
    }

    public void moveHandlerRight() {
        if ((currHandlerIndex + 1) >= elementsCount) {
            return;
        }
        currHandlerIndex += 1;
        updateHandler();
    }

    public void moveHandlerLeft() {
        if (currHandlerIndex - 1 < 0) {
            return;
        }

        currHandlerIndex -= 1;
        updateHandler();
    }

    public void moveHandlerUp() {
        int newIndex = currHandlerIndex - slotGroup.getWidth();
        if (newIndex < 0) {
            return;
        }

        currHandlerIndex = newIndex;
        updateHandler();
    }

    public void moveHandlerDown() {
        int newIndex = currHandlerIndex + slotGroup.getWidth();
        if (newIndex >= elementsCount) {
            return;
        }

        currHandlerIndex = newIndex;
        updateHandler();
    }

    public void displayItemAtHandler() {
        if (elementDisplayHandler == null) {
            return;
        }

        var slot = getSlotAtHandler();
        var displayable = (DisplayableElement<?>)slot.getContent();

        elementDisplayHandler.displayElement(displayable);
    }
}
