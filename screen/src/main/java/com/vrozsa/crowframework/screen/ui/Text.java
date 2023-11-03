package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.screen.ui.components.api.UIText;

public class Text implements UIText {
    private String value;

    public Text() {}

    public Text(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
