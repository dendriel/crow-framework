package com.rozsa.screen.ui.buttongroup;

import com.rozsa.shared.attributes.Border;
import com.rozsa.shared.attributes.Rect;
import com.rozsa.shared.attributes.Size;
import com.rozsa.screen.ui.*;
import com.rozsa.screen.ui.button.UIButton;
import com.rozsa.screen.ui.button.UIButtonTemplate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class UIButtonGroup extends UIBaseComponent<UIButtonGroupTemplate> {
    private final UIButtonGroupTemplate data;
    private final List<UIButton> buttons;
    private final JPanel panel;

    private JScrollPane scrollPane;
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
        setupBackground();
        setupScrollPane();
    }

    private void setupPanel() {
        Size size = data.getSize();
        Size spacing = data.getSpacing();
        panel.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        GridLayout gridLayout = new GridLayout(size.getHeight(), size.getWidth(), spacing.getWidth(), spacing.getHeight());
        panel.setLayout(gridLayout);
        panel.setBackground(new Color(0, 0, 0, 0));
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        Border border = data.getBorder();
        scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(border.getTop(), border.getLeft(), border.getBottom(), border.getRight()), new EtchedBorder()));

        CustomScrollBarUITemplate customScrollBarUITemplate = data.getCustomScrollBarUI();
        if (customScrollBarUITemplate != null) {
            CustomScrollBarUI customScrollBarUI = new CustomScrollBarUI(customScrollBarUITemplate);
            scrollPane.getVerticalScrollBar().setUI(customScrollBarUI);
        }

//        scrollPane.getVerticalScrollBar().setUnitIncrement(100);
        setupBounds();
    }

    private void setupBounds() {
        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();
        scrollPane.setBounds(x, y, rect.getWidth(), rect.getHeight());
    }

    private void setupButtons() {
        int size = data.getWidth() * data.getHeight();
        UIButtonTemplate buttonTemplate = data.getButton();
        buttonTemplate.setReferenceSize(data.getReferenceSize());
        for (int i = 0; i < size; i++) {
            UIButton button = new UIButton(buttonTemplate);
            buttons.add(button);
            panel.add(button.getJButton());
        }
    }

    private void setupBackground() {
        UIIconTemplate bgTemplate = data.getBackground();
        if (bgTemplate == null) {
            return;
        }

        // setup parent offset
        Rect targetRect = bgTemplate.getRect();
        targetRect.setOffset(rect.getOffset().add(targetRect.getOffset()));
        bgTemplate.setRect(targetRect);
        bgTemplate.setReferenceSize(data.getReferenceSize());

        background = new UIIcon(bgTemplate);
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
        container.add(scrollPane);
    }

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        if (expandMode.equals(UIExpandMode.NONE)) {
            return;
        }

        background.updateScreenSize(parentSize);
        setupBounds();
        setupPanel();
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
