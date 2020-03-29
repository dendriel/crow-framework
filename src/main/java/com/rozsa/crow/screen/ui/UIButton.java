package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.sprite.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UIButton extends UIBaseComponent<UIButtonTemplate> {
    private UIButtonTemplate data;
    private JButton button;

    public UIButton(UIButtonTemplate data) {
        this.data = data;
        setup();
    }

    private void setup() {
        button = new JButton();
        setupButton();
    }

    private void setupButton() {
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        // Don't keep selected after a click.
        button.setFocusPainted(false);
        // Don't fill with default button color.
        button.setContentAreaFilled(false);

        setupLabel();
        setupImages();

        Rect rect = data.getRect();
        button.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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

        Image defaultImage = Image.load(data.getDefaultImage());
        BufferedImage defaultBufferedImage = defaultImage.getContent(rect.getWidth(), rect.getHeight());
        button.setIcon(new ImageIcon(defaultBufferedImage));

        Image pressedImage = Image.load(data.getPressedImage());
        BufferedImage pressedBufferedImage = pressedImage.getContent(rect.getWidth(), rect.getHeight());
        button.setPressedIcon(new ImageIcon(pressedBufferedImage));
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
}
