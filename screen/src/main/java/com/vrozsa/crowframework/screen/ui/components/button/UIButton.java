package com.vrozsa.crowframework.screen.ui.components.button;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.AbstractUIComponent;
import com.vrozsa.crowframework.screen.ui.components.UIToolTip;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIToolTipTemplate;
import com.vrozsa.crowframework.screen.ui.listener.UIEventListener;
import com.vrozsa.crowframework.screen.ui.listener.UIEventListenerTuple;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.image.ImageLoader;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides UI button functionality.
 */
public class UIButton extends AbstractUIComponent<UIButtonTemplate> {
    private UIButtonTemplate data;
    private CustomJButton button;
    private final Set<UIEventListenerTuple<UIEventListener>> buttonPressedListeners;
    private final Set<UIEventListenerTuple<UIEventListener>> buttonHeldListeners;
    private final Set<UIEventListenerTuple<UIEventListener>> buttonReleasedListeners;
    private final Set<UIEventListenerTuple<UIEventListener>> buttonMouseEnteredListeners;
    private final Set<UIEventListenerTuple<UIEventListener>> buttonMouseExitedListeners;

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
//        var rect = data.getRect();

        if (data.getDefaultImage() != null) {
            var resizableImage = ImageLoader.load(data.getDefaultImage());
            var bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setIcon(new ImageIcon(bufferedImage));
        }

        if (data.getPressedImage() != null) {
            var resizableImage = ImageLoader.load(data.getPressedImage());
            var bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setPressedIcon(new ImageIcon(bufferedImage));
        }

        if (data.getRolloverImage() != null) {
            var resizableImage = ImageLoader.load(data.getRolloverImage());
            var bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
            button.setRolloverIcon(new ImageIcon(bufferedImage));
        }
        else {
            button.setRolloverEnabled(false);
        }

        if (data.getDisabledImage() != null) {
            var resizableImage = ImageLoader.load(data.getDisabledImage());
            var bufferedImage = resizableImage.getContent(rect.getWidth(), rect.getHeight());
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
        buttonMouseEnteredListeners.forEach(l -> l.listener().onEvent(l.state()));
    }

    public void addMouseEnteredListener(UIEventListener listener, Object state) {
        buttonMouseEnteredListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeMouseEnteredListener(UIEventListener listener) {
        buttonMouseEnteredListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    private void onMouseExited(MouseEvent evt) {
        buttonMouseExitedListeners.forEach(l -> l.listener().onEvent(l.state()));
    }

    public void addMouseExitedListener(UIEventListener listener, Object state) {
        buttonMouseExitedListeners.add(new UIEventListenerTuple<>(listener, state));
    }

    public void removeMouseExitedListener(UIEventListener listener) {
        buttonMouseExitedListeners.remove(new UIEventListenerTuple<>(listener, null));
    }

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        if (expandMode == UIExpandMode.FILL && data.getLabel() != null) {
            var refSize = data.getReferenceSize();
            var font = UIFontTemplate.updateFontTemplate(data.getLabel().getFont(), refSize.getHeight(), parentSize.getHeight());
            button.setFont(font.getJFont());
        }

        setupBounds();
        setupImages();
    }

    private static class CustomJButton extends JButton {
        private final UIToolTipTemplate toolTipTemplate;

        CustomJButton(UIToolTipTemplate toolTipTemplate) {
            this.toolTipTemplate = toolTipTemplate;
        }

        CustomJButton() {
            this.toolTipTemplate = new UIToolTipTemplate();
        }

        @Override
        public JToolTip createToolTip() {
            return (new UIToolTip(toolTipTemplate, this));
        }
    }
}
