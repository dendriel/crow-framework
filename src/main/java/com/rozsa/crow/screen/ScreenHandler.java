package com.rozsa.crow.screen;

import com.rozsa.crow.game.GameLoop;
import com.rozsa.crow.screen.api.WindowCloseRequestListener;
import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ScreenHandler<TScreenKey extends Enum<TScreenKey>> {
    private final ScreenHandlerConfig config;
    private JFrame frame;
    private ConcurrentMap<TScreenKey, BaseScreen> screens;

    private HashSet<WindowCloseRequestListener> onWindowCloseRequestListeners;

    public ScreenHandler(ScreenHandlerConfig config) {
        this(config, null);
    }

    public ScreenHandler(ScreenHandlerConfig config, KeyListener inputListener) {
        this.config = config;
        frame = new JFrame();
        screens = new ConcurrentHashMap<>();
        onWindowCloseRequestListeners = new HashSet<>();
        setup(inputListener);
    }

    private void setup(KeyListener inputListener) {
        setupWindow();

        if (inputListener != null) {
            setupInputListener(inputListener);
        }

        setVisible(config.isVisible());
        GameLoop.setScreenUpdateListener(this::screenUpdate);
    }

    public void addOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.add(listener);
    }

    public void removeOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.remove(listener);
    }

    private void screenUpdate() {
        screens.values().forEach(BaseScreen::draw);
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
        setupCloseOperation();
    }

    public Offset getPosition() {
        Insets insets = frame.getInsets();
        int insetWidth = insets.left;// + insets.right;
        int insetHeight = insets.top;// + insets.bottom;

        Point point = frame.getLocation();

        Offset position = new Offset((int)point.getX(), (int)point.getY());
        position = position.add(new Offset(insetWidth, insetHeight));
        return position;
    }

    private void setupCloseOperation() {
        if (config.isTerminateOnWindowCloseClick()) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            return;
        }

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                onCloseRequest();
            }
        });
    }

    private void onCloseRequest() {
        onWindowCloseRequestListeners.forEach(WindowCloseRequestListener::onWindowClose);
    }

    private void setupWindowed(Size size, boolean isResizable) {
        frame.setSize(size.getWidth(), size.getHeight());
        frame.setResizable(isResizable);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                onScreenResized(componentEvent);
            }
        });
    }

    private void onScreenResized(ComponentEvent componentEvent) {
        Dimension dim = componentEvent.getComponent().getSize();
        int newWidth = removeWidthInsets((int)dim.getWidth());
        int newHeight = removeHeightInsets((int)dim.getHeight());

        Size newSize = new Size(newWidth, newHeight);
        screens.values().forEach(s -> s.updateScreenSize(newSize));
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

    private void compensateInsets(Size size) {
        Insets insets = frame.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;

        if (insetWidth == 0 && insetHeight == 0) {
            return;
        }

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
        compensateInsets(config.getSize().clone());
    }

    // TODO: the method name doesnt make sense if we pass isVisible;
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

    private int removeWidthInsets(int width) {
        Insets insets = frame.getInsets();
        return width - insets.left - insets.right;
    }

    private int removeHeightInsets(int height) {
        Insets insets = frame.getInsets();
        return height - insets.top - insets.bottom;
    }

    public Size getSize() {
        return new Size(getWidth(), getHeight());
    }

    public Size getWindowSize() {
        return new Size(frame.getWidth(), frame.getHeight());
    }
}
