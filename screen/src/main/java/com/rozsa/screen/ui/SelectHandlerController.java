package com.rozsa.screen.ui;

import com.rozsa.screen.ui.api.UISlotGroupHandler;

public class SelectHandlerController extends BaseSlotGroupHandlerController {
    public SelectHandlerController(UISlotGroupHandler slotGroupHandler) {
        super(slotGroupHandler, slotGroupHandler.getSlotGroup().getSlotCount());
    }
}
