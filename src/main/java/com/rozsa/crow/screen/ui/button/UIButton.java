package com.rozsa.crow.screen.ui.button;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.ui.UIBaseComponent;
import com.rozsa.crow.screen.ui.UILabelTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UIButton extends UIBaseComponent<UIButtonTemplate> {
    private UIButtonTemplate data;
    private CustomJButton button;
    private Set<ButtonEventListenerTuple<UIButtonPressedListener>> buttonPressedListeners;
    private Set<ButtonEventListenerTuple<UIButtonMouseEnteredListener>> buttonMouseEnteredListeners;
    private Set<ButtonEventListenerTuple<UIButtonMouseExitedListener>> buttonMouseExitedListeners;

    public UIButton(UIButtonTemplate data) {
        this.data = data;
        buttonPressedListeners = new HashSet<>();
        buttonMouseEnteredListeners = new HashSet<>();
        buttonMouseExitedListeners = new HashSet<>();
        setup();
    }

    private void setup() {
        button = new CustomJButton(data.getToolTip());
        setupButton();
    }

    private void setupButton() {
        setupLayout();
        setupLabel();
        setupImages();
        setupEvents();
        setupToolTip();

        Rect rect = data.getRect();
        button.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        button.setEnabled(!data.isDisabled());
    }

    private void setupLayout() {
        button.setBorder(null);
//        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        // Don't keep selected after a click.
        button.setFocusPainted(false);
        // Don't fill with default button color.
        button.setContentAreaFilled(false);
    }

    private void setupLabel() {
        UILabelTemplate label = data.getLabel();
        Font font = new Font(label.getFont(), label.getStyle(), label.getSize());
        button.setFont(font);
        Color color = label.getColor();
        button.setForeground(color);
        // CENTER = 0; TOP = 1; BOTTOM = 3.
        button.setVerticalTextPosition(label.getVerticalAlignment());
        // CENTER = 0; LEFT = 2; RIGHT = 4.
        button.setHorizontalTextPosition(label.getHorizontalAlignment());
        button.setText(label.getText());
    }

    private void setupImages() {
        Rect rect = data.getRect();

        if (data.getDefaultImage() != null) {
            Image image = Image.load(data.getDefaultImage());
            BufferedImage bufferedImage = image.getContent(rect.getWidth(), rect.getHeight());
            button.setIcon(new ImageIcon(bufferedImage));
        }

        if (data.getPressedImage() != null) {
            Image image = Image.load(data.getPressedImage());
            BufferedImage bufferedImage = image.getContent(rect.getWidth(), rect.getHeight());
            button.setPressedIcon(new ImageIcon(bufferedImage));
        }

        if (data.getRolloverImage() != null) {
            Image image = Image.load(data.getRolloverImage());
            BufferedImage bufferedImage = image.getContent(rect.getWidth(), rect.getHeight());
            button.setRolloverIcon(new ImageIcon(bufferedImage));
        }

        if (data.getDisabledImage() != null) {
            Image image = Image.load(data.getDisabledImage());
            BufferedImage bufferedImage = image.getContent(rect.getWidth(), rect.getHeight());
            button.setDisabledIcon(new ImageIcon(bufferedImage));
        }
    }

    private void setupEvents() {
        button.addActionListener(this::onButtonPressed);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { onMouseEntered(evt); }
            public void mouseExited(MouseEvent evt) { onMouseExited(evt); }
        });
    }

    private void setupToolTip() {
        if (data.getToolTip() != null) {
            button.setToolTipText(String.format("<html><body>%s</body></html>", data.getToolTip().getText()));
        }
    }

    @Override
    public void updateComponentTemplate(UIButtonTemplate data){
        if (this.data.equals(data)) {
            return;
        }

        this.data = data;
        setupButton();
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
    }

    public void show() {
        isEnabled = true;
        button.setVisible(true);
    }

    public void hide() {
        isEnabled = false;
        button.setVisible(false);
    }

    public void setDisabled(boolean isDisabled) {
        // isDisable refer to the button state; isEnabled refer to the component as a whole.
        // When disabled, the button will be displayed using the disabled icon (if set).
        button.setEnabled(!isDisabled);
    }

    @Override
    public void wrapUp(Container container) {
        super.wrapUp(container);
        container.add(button);
    }

    @Override
    public void destroy(Container container) {
        super.destroy(container);
        container.remove(button);
    }


    private void onButtonPressed(ActionEvent e) {
        System.out.println("ButtonPressed");
        buttonPressedListeners.forEach(l -> l.getListener().onButtonPressed(l.getState()));
    }

    public void addButtonPressedListener(UIButtonPressedListener listener, Object state) {
        buttonPressedListeners.add(new ButtonEventListenerTuple<>(listener, state));
    }

    public void removeButtonPressedListener(UIButtonPressedListener listener) {
        buttonPressedListeners.remove(new ButtonEventListenerTuple<>(listener, null));
    }

    private void onMouseEntered(MouseEvent evt) {
        System.out.println("onMouseEntered");
        buttonMouseEnteredListeners.forEach(l -> l.getListener().onMouseEntered(l.getState()));
    }

    public void addMouseEnteredListener(UIButtonMouseEnteredListener listener, Object state) {
        buttonMouseEnteredListeners.add(new ButtonEventListenerTuple<>(listener, state));
    }

    public void removeMouseEnteredListener(UIButtonMouseEnteredListener listener) {
        buttonMouseEnteredListeners.remove(new ButtonEventListenerTuple<>(listener, null));
    }

    private void onMouseExited(MouseEvent evt) {
        System.out.println("onMouseExited");
        buttonMouseExitedListeners.forEach(l -> l.getListener().onMouseExited(l.getState()));
    }

    public void addMouseExitedListener(UIButtonMouseExitedListener listener, Object state) {
        buttonMouseExitedListeners.add(new ButtonEventListenerTuple<>(listener, state));
    }

    public void removeMouseExitedListener(UIButtonMouseExitedListener listener) {
        buttonMouseExitedListeners.remove(new ButtonEventListenerTuple<>(listener, null));
    }

    private class ButtonEventListenerTuple<T> {
        private final T listener;
        private final Object state;

        ButtonEventListenerTuple(T listener, Object state) {
            this.listener = listener;
            this.state = state;
        }

        public T getListener() {
            return listener;
        }

        public Object getState() {
            return state;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ButtonEventListenerTuple that = (ButtonEventListenerTuple) o;
            return Objects.equals(listener, that.listener);
        }

        @Override
        public int hashCode() {
            return Objects.hash(listener);
        }
    }
}
