package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.EnumMap;

public class ScreenHandler<TScreenKey extends Enum<TScreenKey>> {
    private final ScreenHandlerConfig config;
    private JFrame frame;
    private EnumMap<TScreenKey, BaseScreen> screens;

    public ScreenHandler(Class<TScreenKey> keyClazz, ScreenHandlerConfig config) {
        this(keyClazz, config, null);
    }

    public ScreenHandler(Class<TScreenKey> keyClazz, ScreenHandlerConfig config, KeyListener inputListener) {
        this.config = config;
        frame = new JFrame();
        screens = new EnumMap<>(keyClazz);
        setup(inputListener);
    }

    private void setup(KeyListener inputListener) {
        setupWindow();

        if (inputListener != null) {
            setupInputListener(inputListener);
        }

        setVisible(config.isVisible());
    }

    private void setupWindow() {
        frame.setTitle(config.getTitle());

        if (config.isFullscreen()) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        else {
            setupWindowed(config.getSize(), config.isResizable());
        }

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupWindowed(Size size, boolean isResizable) {
        frame.setSize(size.getWidth(), size.getHeight());
        frame.setResizable(isResizable);
    }

    private void setupInputListener(KeyListener inputListener) {
        JTextField textField = new JTextField();
        textField.addKeyListener(inputListener);
        textField.setBorder(null);
        // Allow tab key to be noticed.
        textField.setFocusTraversalKeysEnabled(false);
        // Clear ActionMap so we won't run on special text actions errors (e.g.: shift + letter; ctrl + a; ctrl + c, etc).
        textField.setActionMap(new ActionMap());
        frame.add(textField);
    }

    private void compensateInsets() {
        Insets insets = frame.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;

        if (insetWidth == 0 && insetHeight == 0) {
            return;
        }

        Size size = config.getSize().clone();
        size.addWidth(insetWidth);
        size.addHeight(insetHeight);
        frame.setSize(size.getWidth(), size.getHeight());
        screens.values().forEach(s -> s.updateScreenSize(getSize()));
    }

    public void add(TScreenKey key, BaseScreen screen) {
        screens.put(key, screen);
        frame.add(screen);
        screen.updateScreenSize(getSize());
    }

    public boolean remove(TScreenKey key, BaseScreen screen) {
        if (!screens.containsKey(key)) {
            return false;
        }
        screens.remove(key, screen);
        frame.remove(screen);
        return true;
    }

    public BaseScreen get(TScreenKey key) {
        if (!screens.containsKey(key)) {
            return null;
        }
        return screens.get(key);
    }

    public void exit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
        compensateInsets();
    }

    public void setOnlyVisible(TScreenKey key, boolean isVisible) {
        screens.values().forEach(s -> s.setVisible(false));
        if (screens.containsKey(key)) {
            BaseScreen target = screens.get(key);
            target.setVisible(isVisible);
            target.draw();
        }
    }

    public int getWidth() {
        Insets insets = frame.getInsets();
        return frame.getWidth() - insets.left - insets.right;
    }

    public int getHeight() {
        Insets insets = frame.getInsets();
        return frame.getHeight() - insets.top - insets.bottom;
    }

    public Size getSize() {
        return new Size(getWidth(), getHeight());
    }

    public Size getWindowSize() {
        return new Size(frame.getWidth(), frame.getHeight());
    }
}
