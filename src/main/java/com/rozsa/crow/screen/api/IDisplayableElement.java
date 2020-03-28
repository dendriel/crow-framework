package com.rozsa.crow.screen.api;

import com.rozsa.crow.screen.ui.api.UISlotContent;

public interface IDisplayableElement<T> {
    T getDisplayMode();

    String getName();

    String getDescription();

    UISlotContent getSlotContent();

    int getPrice();

    int getLevel();
}
