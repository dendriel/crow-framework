package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AllArgsConstructor;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

@AllArgsConstructor
public class ScreenHandler {
    private static final LoggerService logger = LoggerService.of(ScreenHandler.class);

    private final ScreenHandlerConfig config;
    private final JFrame frame;
    private final ConcurrentMap<String, Screen> screens;
    private final HashSet<WindowCloseRequestListener> onWindowCloseRequestListeners;

    public ScreenHandler(final ScreenHandlerConfig config) {
        this.config = config;
        frame = new JFrame();
        screens = new ConcurrentHashMap<>();
        onWindowCloseRequestListeners = new HashSet<>();

//        setup();
    }

    public void setup() {
        setupWindow();
        setVisible(config.isVisible());
    }

    public void addOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.add(listener);
    }

    public void removeOnWindowCloseRequestListener(WindowCloseRequestListener listener) {
        onWindowCloseRequestListeners.remove(listener);
    }

    public void update() {
        screens.values().forEach(Screen::draw);
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

    public Offset getPosition() {
        var insets = frame.getInsets();
        var insetWidth = insets.left;// + insets.right;
        var insetHeight = insets.top;// + insets.bottom;

        java.awt.Point point = frame.getLocation();

        Offset position = new Offset((int)point.getX(), (int)point.getY());
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

    public void add(String key, Screen screen) {
        screens.put(key, screen);
        // Smelly... the Screen contract doesn't tell (and should not) anything about a screen being a JPanel.
        frame.add((JPanel)screen);
        screen.resize(getSize());
    }

    public boolean remove(String key, AbstractScreen screen) {
        if (!screens.containsKey(key)) {
            return false;
        }
        screens.remove(key, screen);
        frame.remove(screen);
        return true;
    }

    public Screen getScreen(final String key) {
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
        compensateInsets(config.size().clone());
    }

    public void setOnlyScreenVisible(final String key, final boolean isVisible) {
        screens.values().forEach(s -> s.setVisible(false));
        if (screens.containsKey(key)) {
            Screen target = screens.get(key);
            target.setVisible(isVisible);
            target.draw();
        }
    }

    public int getWidth() {
        var insets = frame.getInsets();
        return frame.getWidth() - insets.left - insets.right;
    }

    public int getHeight() {
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

    public Size getSize() {
        return new Size(getWidth(), getHeight());
    }

    public Size getWindowSize() {
        return new Size(frame.getWidth(), frame.getHeight());
    }

    /**
     * Setup a key listener to listen for keyboard inputs.
     * <p>
     *     WARNING: must be called before setup().
     * </p>
     * @param keysListener the key listener callbacks.
     */
    public void setupKeysListener(KeysListener keysListener) {
        var textField = new JTextField();
        textField.addKeyListener(new KeyListener() {
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
        });
        textField.setBorder(null);
        // Allow tab key to be noticed.
        textField.setFocusTraversalKeysEnabled(false);
        // Clear ActionMap so we won't run on special text actions errors (e.g.: shift + letter; ctrl + a; ctrl + c, etc).
        textField.setActionMap(new ActionMap());
        frame.add(textField);
    }

    /**
     * Setup a pointer listener to listen for mouse inputs.
     * @param pointerListener the pointer listener callbacks.
     */
    public void setupPointerListener(PointerListener pointerListener) {
        frame.addMouseListener(new MouseListener() {
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
        });
    }
}
