package com.vrozsa.crowframework.sample.screen;

import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.factory.ScreenHandlerFactory;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.UILabel;
import com.vrozsa.crowframework.screen.ui.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.button.UIButton;
import com.vrozsa.crowframework.screen.ui.button.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.listener.UIEventListener;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Rect;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;

public class ButtonInteraction {

    public static void main(String[] args) {

        ScreenHandler screenHandler = ScreenHandlerFactory.createWithSimpleScreen(BACKGROUND_IMAGE_FILE);

        SimpleScreen screen = (SimpleScreen)screenHandler.getScreen(ScreenType.SIMPLE.name());
        Rect screenRect = new Rect(0, 0, screen.getWidth(), screen.getHeight());

        // Dynamic Label
        UILabelTemplate labelData = new UILabelTemplate();
        labelData.setText("Test text");
        labelData.setColor(new Color(255, 255, 255));
        labelData.setFont(new UIFontTemplate("Serif", 0, 36));
        labelData.setVerticalAlignment(0);
        labelData.setHorizontalAlignment(0);
        labelData.setRect(Rect.of(screenRect.centerX() - 200, screenRect.centerY() -100, 400, 50));
        labelData.setReferenceSize(screenRect.getSize());
        UILabel label = new UILabel(labelData);

        UILabelTemplate labelTemplate = labelData.clone();
        labelTemplate.getFont().setSize(24);
        labelTemplate.setText("Click ");
        labelTemplate.setReferenceSize(screenRect.getSize());

        UIButtonTemplate buttonData = new UIButtonTemplate();
        buttonData.setRect(new Rect(screenRect.centerX() - 100, screenRect.centerY() - 25, 200, 50));
        buttonData.setLabel(labelTemplate);
        buttonData.setExpandMode(UIExpandMode.FILL);
        buttonData.setDefaultImage("/assets/ui/button.png");
        buttonData.setPressedImage("/assets/ui/button_pressed.png");
        buttonData.setReferenceSize(screenRect.getSize());
        buttonData.setDisabledImage("/assets/ui/button_pressed.png");
        buttonData.setDisabled(false);

        int i = 3;
        UIButton button = new UIButton(buttonData);
        UIEventListener listener = (s) -> label.setText("Button " + s + " pressed!");
        button.addButtonPressedListener(listener, i);

        UIEventListener heldListener = (s) -> label.setText("Button " + s + " held!");
        button.addButtonHeldListener(heldListener, i);
        UIEventListener releasedListener = (s) -> label.setText("Button " + s + " released!");
        button.addButtonReleasedListener(releasedListener, i);

        UIEventListener onMouseEntered = (s) -> label.setText("Mouse entered " + s);
        button.addMouseEnteredListener(onMouseEntered, i);
        UIEventListener onMouseExited = (s) -> label.setText("Mouse exited " + s);
        button.addMouseExitedListener(onMouseExited, i);


//        screen.addComponent(label);
//        screen.addComponent(button);
        screen.displayView();
    }
}
