package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Crow Window Handler implementation.
 */
final class CrowWindowHandler implements WindowHandler {
    private static final LoggerService logger = LoggerService.of(CrowWindowHandler.class);
    private final WindowHandlerConfig config;
    private final JFrame frame;
    private final ConcurrentMap<String, Screen> screens;
    private final HashSet<WindowCloseRequestListener> onWindowCloseRequestListeners;
    private boolean isInitialized;

    private CrowWindowHandler(final WindowHandlerConfig config) {
        this.config = config;
        frame = new JFrame();
        screens = new ConcurrentHashMap<>();
        onWindowCloseRequestListeners = new HashSet<>();
    }

    public static CrowWindowHandler create(final WindowHandlerConfig config) {
        return new CrowWindowHandler(config);
    }

    @Override
    public void initialize() {
        if (isInitialized) {
            return;
        }

        setupWindow();
        setVisible(config.isVisible());
        isInitialized = true;
    }

    private void setupWindow() {
        frame.setTitle(config.title());

        if (config.isFullscreen()) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }
        else {
            setupWindowed(config.size(), config.isResizable());
        }

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setLayout(null);
        setupCloseOperation();
    }

    @Override
    public void update() {
        screens.values().forEach(Screen::draw);
    }

    @Override
    public Offset getPosition() {
        var insets = frame.getInsets();
        var insetWidth = insets.left;
        var insetHeight = insets.top;

        var point = frame.getLocation();

        var position = new Offset((int)point.getX(), (int)point.getY());
        position = position.sum(new Offset(insetWidth, insetHeight));
        return position;
    }

    private void setupCloseOperation() {
        if (config.terminateOnWindowCloseClick()) {
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

    @Override
    public void addOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.add(listener);
    }

    @Override
    public void removeOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.remove(listener);
    }

    @Override
    public void terminate() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void setupWindowed(Size size, boolean isResizable) {
        frame.setSize(size.getWidth(), size.getHeight());
        frame.setResizable(isResizable);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                onScreenResized(componentEvent);
            }
        });
    }

    private void onScreenResized(ComponentEvent componentEvent) {
        java.awt.Dimension dim = componentEvent.getComponent().getSize();
        int newWidth = removeWidthInsets((int)dim.getWidth());
        int newHeight = removeHeightInsets((int)dim.getHeight());

        var newSize = new Size(newWidth, newHeight);
        screens.values().forEach(s -> s.resize(newSize));
    }

    private void compensateInsets(Size size) {
        var insets = frame.getInsets();
        int insetWidth = insets.left + insets.right;
        int insetHeight = insets.top + insets.bottom;

        if (insetWidth == 0 && insetHeight == 0) {
            return;
        }

        size.addWidth(insetWidth);
        size.addHeight(insetHeight);
        frame.setSize(size.getWidth(), size.getHeight());
        screens.values().forEach(s -> s.resize(getSize()));
    }

    @Override
    public void addScreen(final Screen screen) {
        if (screen instanceof AbstractScreen abstractScreen) {
            screens.put(screen.getName(), screen);
            frame.add(abstractScreen);
            screen.resize(getSize());
            return;
        }
        logger.error("Screen identifier as '{0}' is not a valid screen!", screen.getName());
    }

    public void removeScreen(String name) {
        if (!screens.containsKey(name)) {
            return;
        }

        var screen = screens.get(name);

        if (screen instanceof AbstractScreen abstractScreen) {
            screens.remove(name, screen);
            frame.remove(abstractScreen);
            return;
        }

        // should not get here because we don't allow unknown screens to be added.
        logger.error("Screen identified as '{0}' is not a valid screen!");
    }

    public Screen getScreen(final String name) {
        if (!screens.containsKey(name)) {
            logger.warn("There is no screen identified as '{0}'", name);
            return null;
        }
        return screens.get(name);
    }

    @Override
    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
        compensateInsets(config.size().clone());
    }

    @Override
    public void setScreenVisible(String name, boolean isVisible) {
        screens.values().forEach(s -> s.setVisible(false));
        if (!screens.containsKey(name)) {
            return;
        }

        var target = screens.get(name);
        target.setVisible(isVisible);
        target.draw();
    }

    @Override
    public Size getSize() {
        return new Size(getWidth(), getHeight());
    }

    @Override
    public Size getFrameSize() {
        return new Size(frame.getWidth(), frame.getHeight());
    }

    private int getWidth() {
        var insets = frame.getInsets();
        return frame.getWidth() - insets.left - insets.right;
    }

    private int getHeight() {
        var insets = frame.getInsets();
        return frame.getHeight() - insets.top - insets.bottom;
    }

    private int removeWidthInsets(int width) {
        var insets = frame.getInsets();
        return width - insets.left - insets.right;
    }

    private int removeHeightInsets(int height) {
        var insets = frame.getInsets();
        return height - insets.top - insets.bottom;
    }

    @Override
    public void setupKeysListener(KeysListener keysListener) {
        var textField = new JTextField();
        textField.addKeyListener(new CrowKeysListener(keysListener));
        textField.setBorder(null);
        // Allow the tab key to be noticed.
        textField.setFocusTraversalKeysEnabled(false);
        // Clear ActionMap so we won't run on special text actions errors (e.g.: shift + letter; ctrl + a; ctrl + c, etc).
        textField.setActionMap(new ActionMap());
        frame.add(textField);
    }

    @Override
    public void setupPointerListener(PointerListener pointerListener) {
        frame.addMouseListener(new CrowMouseListener(pointerListener));
    }

    /**
     * Wraps the key events from java.awt into Crow key events.
     */
    private static final class CrowKeysListener implements KeyListener {
        private final KeysListener keysListener;

        private CrowKeysListener(KeysListener keysListener) {
            this.keysListener = keysListener;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keysListener.onKeyTyped(InputKey.from(keyCode));
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keysListener.onKeyPressed(InputKey.from(keyCode));
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keysListener.onKeyReleased(InputKey.from(keyCode));
        }
    }

    /**
     * Wraps the mouse events from java.util.EventListener into Crow pointer events.
     */
    private static final class CrowMouseListener implements MouseListener {
        private final PointerListener pointerListener;

        public CrowMouseListener(PointerListener pointerListener) {
            this.pointerListener = pointerListener;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            var pos = Offset.of(e.getX(), e.getY());
            pointerListener.onPointerClicked(pos);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            var pos = Offset.of(e.getX(), e.getY());
            pointerListener.onPointerPressed(pos);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            var pos = Offset.of(e.getX(), e.getY());
            pointerListener.onPointerReleased(pos);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // skip.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // skip.
        }
    }
}
