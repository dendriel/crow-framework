package com.rozsa.crow.screen.ui.buttongroup;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.UIBaseComponent;
import com.rozsa.crow.screen.ui.UIIcon;
import com.rozsa.crow.screen.ui.UIIconTemplate;
import com.rozsa.crow.screen.ui.button.UIButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class UIButtonGroup extends UIBaseComponent<UIButtonGroupTemplate> {
    private final UIButtonGroupTemplate data;
    private final List<UIButton> buttons;
    private final JPanel panel;

    private UIIcon background;

    public UIButtonGroup(UIButtonGroupTemplate data) {
        super(data);
        this.data = data;

        buttons = new ArrayList<>();
        panel = new JPanel();
        setup();
    }

    private void setup() {
        rect = data.getRect();
        setupPanel();
        setupButtons();
        setupIcon();
    }

    private void setupPanel() {
        Size spacing = data.getSpacing();
        panel.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        FlowLayout flow = new FlowLayout();
        flow.setHgap(spacing.getWidth());
        flow.setVgap(spacing.getHeight());

        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setLayout(flow);
    }

    private void setupButtons() {
        int size = data.getWidth() * data.getHeight();
        for (int i = 0; i < size; i++) {
            UIButton button = new UIButton(data.getButton());
            buttons.add(button);
            panel.add(button.getJButton());
        }
    }

    private void setupIcon() {
        UIIconTemplate bgTemplate = data.getBackground();
        if (bgTemplate == null) {
            return;
        }

        background = new UIIcon(bgTemplate);
        // setup parent offset
        Rect targetRect = background.getRect();
        targetRect.setOffset(rect.getOffset());
        background.setRect(targetRect);
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
        container.add(panel);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) return;

        if (background != null) {
            background.paint(g, observer);
        }
    }

    @Override
    public void updateComponentTemplate(UIButtonGroupTemplate data) {
        throw new UnsupportedOperationException();
    }
}
