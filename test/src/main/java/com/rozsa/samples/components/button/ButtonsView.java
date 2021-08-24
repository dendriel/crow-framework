package com.rozsa.samples.components.button;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.*;
import com.rozsa.crow.screen.ui.button.*;
import com.rozsa.crow.screen.ui.listener.UIEventListener;

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
        backgroundData.setReferenceSize(rect.getSize());
        UIIcon backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);

        // Dynamic Label
        UILabelTemplate labelData = new UILabelTemplate();
        labelData.setText("Test text");
        labelData.setColor(new Color(255, 255, 255));
        labelData.setFont(new UIFontTemplate("Serif", 0, 36));
        labelData.setVerticalAlignment(0);
        labelData.setHorizontalAlignment(0);
        labelData.setRect(rect);
        labelData.setReferenceSize(rect.getSize());
        UILabel label = new UILabel(labelData);
        addComponent(label);

        // Button
        int buttonCount = 8;
        int width = rect.getWidth() / buttonCount;
        for (int i = 0; i < buttonCount; i++) {
            UILabelTemplate labelTemplate = labelData.clone();
            labelTemplate.getFont().setSize(24);
            labelTemplate.setText("Click " + i);
            labelTemplate.setReferenceSize(rect.getSize());

            UIButtonTemplate buttonData = new UIButtonTemplate();
            buttonData.setRect(new Rect(width*i, 0, width, 100));
            buttonData.setLabel(labelTemplate);
            buttonData.setExpandMode(UIExpandMode.FILL);
            buttonData.setDefaultImage("/images/button.png");
            buttonData.setPressedImage("/images/button_pressed.png");
            buttonData.setReferenceSize(rect.getSize());
            if (i % 2 == 0) {
                buttonData.setDisabledImage("/images/button_pressed.png");
                buttonData.setDisabled(true);
            }

            UIToolTipTemplate toolTip = new UIToolTipTemplate();
            toolTip.setText("Test text tooltip <br /> LOOOL");
            toolTip.setBackgroundColor(new Color(0, 100, 0));
            toolTip.setBackgroundEnabled(true);
            toolTip.setColor(new Color(255, 255, 255));
            toolTip.setFont(new UIFontTemplate("Serif", 0, 24));

            UIBorderTemplate border = new UIBorderTemplate();
            border.setStyle(UIBorderTemplate.BorderStyle.ETCHED);
            border.setColorA(new Color(0, 0, 255));
            border.setColorB(new Color(255, 0, 0));
            border.setColorC(new Color(0, 0, 255));
            border.setColorD(new Color(255, 0, 0));
            toolTip.setBorder(border);

            buttonData.setToolTip(toolTip);

            UIButton button = new UIButton(buttonData);
            UIEventListener listener = (s) -> label.setText("Button " + s + " pressed!");
            button.addButtonPressedListener(listener, i);

            if (i % 3 == 0) {
                UIEventListener heldListener = (s) -> label.setText("Button " + s + " held!");
                button.addButtonHeldListener(heldListener, i);
                UIEventListener releasedListener = (s) -> label.setText("Button " + s + " released!");
                button.addButtonReleasedListener(releasedListener, i);
            }

            if (i % 2 == 0) {
                UIEventListener onMouseEntered = (s) -> label.setText("Mouse entered " + s);
                button.addMouseEnteredListener(onMouseEntered, i);
                UIEventListener onMouseExited = (s) -> label.setText("Mouse exited " + s);
                button.addMouseExitedListener(onMouseExited, i);
            }

            addComponent(button);
        }
    }
}
