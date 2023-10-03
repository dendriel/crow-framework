package com.rozsa.screen.ui;

import com.rozsa.screen.api.IDisplayableElement;
import com.rozsa.screen.api.IElementDisplayHandler;
import com.rozsa.screen.ui.api.UISlotGroupHandler;

public class BaseSlotGroupHandlerController {
    protected final UISlotGroupHandler slotGroupHandler;
    protected final UISlotGroup slotGroup;
    protected final IElementDisplayHandler elementDisplayHandler;
    protected int currHandlerIndex;
    protected int elementsCount;

    public BaseSlotGroupHandlerController(UISlotGroupHandler slotGroupHandler, int currMaxItemsSize) {
        this(slotGroupHandler, null, currMaxItemsSize);
    }

    public BaseSlotGroupHandlerController(UISlotGroupHandler slotGroupHandler, IElementDisplayHandler elementDisplayHandler, int currSlotsCount) {
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
        UISlot slot = getSlotAtHandler();
        IDisplayableElement displayable = (IDisplayableElement)slot.getContent();

        elementDisplayHandler.displayElement(displayable);
    }
}
