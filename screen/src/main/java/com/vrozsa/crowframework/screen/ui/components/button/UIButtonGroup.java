package com.vrozsa.crowframework.screen.ui.components.button;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.AbstractUIComponent;
import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonGroupTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class UIButtonGroup extends AbstractUIComponent<UIButtonGroupTemplate> {
    private static final LoggerService logger = LoggerService.of(UIButtonGroup.class);

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
        setupBackground();
        setupPanel();
        setupButtons();
        setupScrollPane();
    }

    private void setupPanel() {
        panel.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        var size = data.getSize();
        var spacing = data.getSpacing();
        var gridLayout = new GridLayout(size.getHeight(), size.getWidth(), spacing.getWidth(), spacing.getHeight());
        panel.setLayout(gridLayout);

        if (isNull(data.getBackground())) {
            panel.setBackground(data.getBackgroundColor().getJColor());
        }
        else {
            panel.setBackground(new Color(0, 0, 0, 0));
        }
    }

    private void setupScrollPane() {
        scrollPane = new JScrollPane(panel);
        // TODO: allow to configure
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(0, 0, 0, 0));

        var border = data.getBorder();
        scrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        new EmptyBorder(border.getTop(), border.getLeft(), border.getBottom(), border.getRight()), new EtchedBorder()
                )
        );

        var customScrollBarUITemplate = data.getCustomScrollBarUI();
        if (customScrollBarUITemplate != null) {
            var customScrollBarUI = new CustomScrollBarUI(customScrollBarUITemplate);
            scrollPane.getVerticalScrollBar().setUI(customScrollBarUI);
        }

        scrollPane.getVerticalScrollBar().setUnitIncrement(data.getScrollIncrement());
        setupBounds();
    }

    private void setupBounds() {
        int x = rect.getX() + parentOffset.getX();
        int y = rect.getY() + parentOffset.getY();
        scrollPane.setBounds(x, y, rect.getWidth(), rect.getHeight());
    }

    private void setupButtons() {
        int size = data.getSize().getWidth() * data.getSize().getHeight();
        UIButtonTemplate buttonTemplate = data.getButton();
        buttonTemplate.setReferenceSize(data.getReferenceSize());
        for (var i = 0; i < size; i++) {
            var button = new UIButton(buttonTemplate);
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
        var targetRect = bgTemplate.getRect();
        targetRect.setOffset(rect.getOffset().sum(targetRect.getOffset()));
        bgTemplate.setRect(targetRect);
        bgTemplate.setReferenceSize(data.getReferenceSize());

        background = UIIcon.from(bgTemplate);
    }

    @Override
    public void show() {
        isEnabled = true;
        scrollPane.setVisible(true);
    }

    @Override
    public void hide() {
        isEnabled = false;
        scrollPane.setVisible(false);
    }

    @Override
    public void wrapUp(Container container) {
        logger.debug("WrapUp called. Container: {0}", container);
        container.add(scrollPane);
    }

    @Override
    public void destroy(Container container) {
        super.destroy(container);
        container.remove(scrollPane);
    }

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        if (expandMode == UIExpandMode.NONE) {
            return;
        }

        background.updateScreenSize(parentSize);
        setupBounds();
        setupPanel();
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (!isEnabled) {
            return;
        }

        if (background != null) {
            background.paint(g, observer);
        }
    }

    @Override
    public void updateComponentTemplate(UIButtonGroupTemplate data) {
        throw new UnsupportedOperationException();
    }
}
