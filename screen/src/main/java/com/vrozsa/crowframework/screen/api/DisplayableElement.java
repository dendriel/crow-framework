package com.vrozsa.crowframework.screen.api;

import com.vrozsa.crowframework.screen.ui.api.UISlotContent;

public interface DisplayableElement<T> {
    T getDisplayMode();

    String getName();

    UISlotContent getSlotContent();
}
