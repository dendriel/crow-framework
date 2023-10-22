package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.screen.ui.api.UISlotGroupHandler;

public class SelectHandlerController extends BaseSlotGroupHandlerController {
    public SelectHandlerController(UISlotGroupHandler slotGroupHandler) {
        super(slotGroupHandler, slotGroupHandler.getSlotGroup().getSlotCount());
    }
}
