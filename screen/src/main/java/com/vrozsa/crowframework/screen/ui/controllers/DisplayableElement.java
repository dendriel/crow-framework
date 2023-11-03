package com.vrozsa.crowframework.screen.ui.controllers;

import com.vrozsa.crowframework.screen.ui.components.api.UISlotContent;

public interface DisplayableElement<T> {
    T getDisplayMode();

    String getName();

    UISlotContent getSlotContent();
}
