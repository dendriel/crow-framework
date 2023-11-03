package com.vrozsa.crowframework.screen.ui.controllers;

import com.vrozsa.crowframework.screen.ui.components.api.UISlotGroupHandler;

public class SelectHandlerController extends BaseSlotGroupHandlerController {
    public SelectHandlerController(UISlotGroupHandler slotGroupHandler) {
        super(slotGroupHandler, slotGroupHandler.getSlotGroup().getSlotCount());
    }
}
