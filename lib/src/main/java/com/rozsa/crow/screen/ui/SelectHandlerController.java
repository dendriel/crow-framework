package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.ui.api.UISlotGroupHandler;

public class SelectHandlerController extends BaseSlotGroupHandlerController {
    public SelectHandlerController(UISlotGroupHandler slotGroupHandler) {
        super(slotGroupHandler, slotGroupHandler.getSlotGroup().getSlotCount());
    }
}
