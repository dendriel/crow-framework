package com.rozsa.screen.api;

import com.rozsa.screen.ui.api.UISlotContent;

public interface IDisplayableElement<T> {
    T getDisplayMode();

    String getName();

    String getDescription();

    UISlotContent getSlotContent();

    int getPrice();

    int getLevel();
}
