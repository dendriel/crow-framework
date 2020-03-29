package com.rozsa.samples.components.button;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.*;
import com.rozsa.crow.screen.ui.button.*;

public class ButtonsView extends BaseView {
    ButtonsView(Rect rect) {
        super(rect);

        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {
        // Icon (static image) component setup.
        UIIconTemplate backgroundData = new UIIconTemplate();
        backgroundData.setImageFile("/images/test_bg_1920x1080.png");
        backgroundData.setRect(rect);
        UIIcon backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);

        // Dynamic Label
        UILabelTemplate labelData = new UILabelTemplate();
        labelData.setText("Test text");
        labelData.setColor(new Color(255, 255, 255));
        labelData.setFont("Serif");
        labelData.setStyle(0);
        labelData.setSize(36);
        labelData.setVerticalAlignment(0);
        labelData.setHorizontalAlignment(0);
        labelData.setRect(rect);
        UILabel label = new UILabel(labelData);
        addComponent(label);

        // Button
        for (int i = 0; i < 8; i++) {
            UILabelTemplate labelTemplate = labelData.clone();
            labelTemplate.setSize(24);
            labelTemplate.setText("Click " + i);

            UIButtonTemplate buttonData = new UIButtonTemplate();
            buttonData.setRect(new Rect(100*i, 0, 100, 100));
            buttonData.setLabel(labelTemplate);
            buttonData.setDefaultImage("/images/button_bg.png");
            buttonData.setPressedImage("/images/button_pressed.png");
            if (i % 2 == 0) {
                buttonData.setDisabledImage("/images/button_pressed.png");
                buttonData.setDisabled(true);
            }

            UIButton button = new UIButton(buttonData);
            UIButtonPressedListener listener = (s) -> label.setText("Button " + s + " pressed!");
            button.addButtonPressedListener(listener, i);

            if (i % 2 == 0) {
                UIButtonMouseEnteredListener onMouseEntered = (s) -> label.setText("Mouse entered " + s);
                button.addMouseEnteredListener(onMouseEntered, i);
                UIButtonMouseExitedListener onMouseExited = (s) -> label.setText("Mouse exited " + s);
                button.addMouseExitedListener(onMouseExited, i);
            }

            addComponent(button);
        }
    }
}
