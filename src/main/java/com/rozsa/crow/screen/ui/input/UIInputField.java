package com.rozsa.crow.screen.ui.input;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIBaseComponent;
import com.rozsa.crow.screen.ui.listener.UIEventListener;
import com.rozsa.crow.screen.ui.listener.UIEventListenerTuple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Set;

public class UIInputField extends UIBaseComponent<UIInputFieldTemplate> {
    private UIInputFieldTemplate data;
    private JTextField textField;
    private Set<UIEventListenerTuple<UIEventListener>> inputFieldSubmittedListeners;

    public UIInputField(UIInputFieldTemplate data) {
        super(data);
        this.data = data;
        inputFieldSubmittedListeners = new HashSet<>();
        setup();
    }

    public String getText() {
        return textField.getText();
    }

    private void setup() {
        if (data.isPasswordInput()) {
            textField = new CustomJPasswordField(data.getColumns(), data.getToolTip());
        } else {
            textField = new CustomJTextField(data.getColumns(), data.getToolTip());
        }
        setupInput();
    }

    private void setupInput() {
        setupStyle();
        setupToolTip();

        Rect rect = data.getRect();
        textField.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        textField.setEnabled(!data.isDisabled());

        textField.addActionListener(this::onSubmitted);
    }

    private void setupStyle() {
        textField.setFont(data.getFont().getJFont());
        if (data.getFontColor() != null) {
            textField.setForeground(data.getFontColor().getJColor());
        }

        if (data.getBackgroundColor() != null) {
            textField.setBackground(data.getBackgroundColor().getJColor());
        }

        textField.setOpaque(data.isBackgroundVisible());

        if (data.getBorder() != null) {
            textField.setBorder(data.getBorder().getJBorder());
        }
    }

    private void setupToolTip() {
        if (data.getToolTip() != null) {
            textField.setToolTipText(String.format("<html><body>%s</body></html>", data.getToolTip().getText()));
        }
    }

    public void setDisabled(boolean isDisabled) {
        // isDisable refer to the button state; isEnabled refer to the component as a whole.
        // When disabled, the button will be displayed using the disabled icon (if set).
        textField.setEnabled(!isDisabled);
    }

    private void onSubmitted(ActionEvent e) {
        System.out.println("Input submit");
        inputFieldSubmittedListeners.forEach(l -> l.getListener().onEvent(l.getState()));
    }

    public void addInputFieldSubmittedListener(UIEventListener listener, Object state) {
        inputFieldSubmittedListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeInputFieldSubmittedListener(UIEventListener listener) {
        inputFieldSubmittedListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
    }

    @Override
    public void updateComponentTemplate(UIInputFieldTemplate data) {
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setupInput();
    }

    @Override
    public void show() {
        isEnabled = true;
    }

    @Override
    public void hide() {
        isEnabled = false;
    }

    @Override
    public void wrapUp(Container container) {
        super.wrapUp(container);
        container.add(textField);
    }

    @Override
    public void destroy(Container container) {
        super.destroy(container);
        container.remove(textField);
    }
}
