package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.ui.api.UIText;

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
