package com.rozsa.crow.screen.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class UIScrollPane extends UIBaseComponent<UIScrollPaneTemplate> {
    private final UIScrollPaneTemplate data;
    private JScrollPane scrollPane;

    public UIScrollPane(UIScrollPaneTemplate data) {
        super(data);
        this.data = data;

        setup();
    }

    private void setup() {
        this.rect = data.getRect();

        JPanel panel = new JPanel();
        panel.setBounds(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton("Hello-" + i);
            button.setBounds(0, 25*i, 60, 20);
            panel.add(button);
        }

        scrollPane = new JScrollPane(panel);
//        scrollPane = new JScrollPane(new JButton("Hello-world"));
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(rect.getX(), rect.getY(), rect.getWidth()/2, rect.getHeight()/2);
//        JPanel contentPane = new JPanel(null);
//        contentPane.setPreferredSize(new Dimension(500, 400));
//        contentPane.add(scrollPane);
//        frame.setContentPane(contentPane);
//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setVisible(true);
    }

    @Override
    public void wrapUp(Container container) {
        super.wrapUp(container);
        container.add(scrollPane);
    }

    @Override
    public void destroy(Container container) {
        super.destroy(container);
        container.remove(scrollPane);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {

    }

    @Override
    public void updateComponentTemplate(UIScrollPaneTemplate data) throws IOException {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
