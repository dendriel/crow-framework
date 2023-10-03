package com.rozsa.screen.ui.button;

import com.rozsa.shared.api.screen.Image;
import com.rozsa.screen.sprite.ImageLoader;
import com.rozsa.shared.attributes.Offset;
import com.rozsa.shared.attributes.Rect;
import com.rozsa.shared.attributes.Size;
import com.rozsa.screen.ui.UIBaseComponent;
import com.rozsa.screen.ui.UIExpandMode;
import com.rozsa.screen.ui.UIFontTemplate;
import com.rozsa.screen.ui.UILabelTemplate;
import com.rozsa.screen.ui.listener.UIEventListener;
import com.rozsa.screen.ui.listener.UIEventListenerTuple;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Set;

public class UIButton extends UIBaseComponent<UIButtonTemplate> {
    private UIButtonTemplate data;
    private CustomJButton button;
    private Set<UIEventListenerTuple<UIEventListener>> buttonPressedListeners;
    private Set<UIEventListenerTuple<UIEventListener>> buttonHeldListeners;
    private Set<UIEventListenerTuple<UIEventListener>> buttonReleasedListeners;
    private Set<UIEventListenerTuple<UIEventListener>> buttonMouseEnteredListeners;
    private Set<UIEventListenerTuple<UIEventListener>> buttonMouseExitedListeners;

    private boolean isPressed;

    public UIButton(UIButtonTemplate data) {
        super(data);
        this.data = data;
        parentOffset = Offset.origin();
        buttonPressedListeners = new HashSet<>();
        buttonHeldListeners = new HashSet<>();
        buttonReleasedListeners = new HashSet<>();
        buttonMouseEnteredListeners = new HashSet<>();
        buttonMouseExitedListeners = new HashSet<>();
        setup();
    }

    private void setup() {
        button = new CustomJButton(data.getToolTip());
        rect = data.getRect();
        setupButton();
    }

    private void setupButton() {
        setupLayout();
        setupLabel();
        setupImages();
        setupEvents();
        setupToolTip();
        setupBounds();

        button.setFocusable(data.isFocusable());
        button.setEnabled(!data.isDisabled());
    }

    public JButton getJButton() {
        return button;
    }

    private void setupBounds() {
        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();
        button.setBounds(x, y, rect.getWidth(), rect.getHeight());
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
        if (label == null) {
            return;
        }
        button.setFont(label.getFont().getJFont());
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
            Image resizableImage = ImageLoader.load(data.getDefaultImage());
            BufferedImage bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setIcon(new ImageIcon(bufferedImage));
        }

        if (data.getPressedImage() != null) {
            Image resizableImage = ImageLoader.load(data.getPressedImage());
            BufferedImage bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setPressedIcon(new ImageIcon(bufferedImage));
        }

        if (data.getRolloverImage() != null) {
            Image resizableImage = ImageLoader.load(data.getRolloverImage());
            BufferedImage bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setRolloverIcon(new ImageIcon(bufferedImage));
        }
        else {
            button.setRolloverEnabled(false);
        }

        if (data.getDisabledImage() != null) {
            Image resizableImage = ImageLoader.load(data.getDisabledImage());
            BufferedImage bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setDisabledIcon(new ImageIcon(bufferedImage));
        }
    }

    private void setupEvents() {
        button.addActionListener(this::onButtonPressed);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { onMouseEntered(evt); }
            public void mouseExited(MouseEvent evt) { onMouseExited(evt); }
        });

        button.getModel().addChangeListener(this::onButtonChanged);
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
        buttonPressedListeners.forEach(l -> l.listener().onEvent(l.state()));
    }

    private void onButtonChanged(ChangeEvent e) {
        ButtonModel model = (ButtonModel) e.getSource();
        if (model.isPressed() == isPressed) {
            return;
        }

        isPressed = model.isPressed();
        if (isPressed) {
            buttonHeldListeners.forEach(l -> l.listener().onEvent(l.state()));
        } else {
            buttonReleasedListeners.forEach(l -> l.listener().onEvent(l.state()));
        }
    }

    public void addButtonPressedListener(UIEventListener listener, Object state) {
        buttonPressedListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeButtonPressedListener(UIEventListener listener) {
        buttonPressedListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    public void addButtonHeldListener(UIEventListener listener, Object state) {
        buttonHeldListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeButtonHeldListener(UIEventListener listener) {
        buttonHeldListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    public void addButtonReleasedListener(UIEventListener listener, Object state) {
        buttonReleasedListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeButtonReleasedListener(UIEventListener listener) {
        buttonReleasedListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    private void onMouseEntered(MouseEvent evt) {
//        System.out.println("onMouseEntered");
        buttonMouseEnteredListeners.forEach(l -> l.listener().onEvent(l.state()));
    }

    public void addMouseEnteredListener(UIEventListener listener, Object state) {
        buttonMouseEnteredListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeMouseEnteredListener(UIEventListener listener) {
        buttonMouseEnteredListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    private void onMouseExited(MouseEvent evt) {
//        System.out.println("onMouseExited");
        buttonMouseExitedListeners.forEach(l -> l.listener().onEvent(l.state()));
    }

    public void addMouseExitedListener(UIEventListener listener, Object state) {
        buttonMouseExitedListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeMouseExitedListener(UIEventListener listener) {
        buttonMouseExitedListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);
        if (expandMode.equals(UIExpandMode.FILL) && data.getLabel() != null) {
            Size refSize = data.getReferenceSize();
            UIFontTemplate font = UIFontTemplate.updateFontTemplate(data.getLabel().getFont(), refSize.getHeight(), parentSize.getHeight());
            button.setFont(font.getJFont());
        }

        setupBounds();
        setupImages();
    }
}
