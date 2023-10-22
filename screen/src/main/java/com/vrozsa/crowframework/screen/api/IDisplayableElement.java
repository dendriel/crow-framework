package com.vrozsa.crowframework.screen.api;

import com.vrozsa.crowframework.screen.ui.api.UISlotContent;

public interface IDisplayableElement<T> {
    T getDisplayMode();

    String getName();

    String getDescription();

    UISlotContent getSlotContent();

    int getPrice();

    int getLevel();
}
