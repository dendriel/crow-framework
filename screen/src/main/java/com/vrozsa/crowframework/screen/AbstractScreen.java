package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class to be used to declare custom screens via an extension.
 */
public abstract class AbstractScreen extends JPanel implements Screen {
    private final String name;
    private final Map<String, View> views;
    private final Map<String, List<String>> viewGroup;

    private String lastViewGroupSet;

    protected AbstractScreen(final String name, final Size size) {
        this(name, size, Color.blue());
    }

    protected AbstractScreen(final String name, final Size size, final Color bgColor) {
        this.name = name;
        views = new HashMap<>();
        viewGroup = new HashMap<>();

        setup(size, bgColor);
    }

    private void setup(Size size, Color color) {
        setSize(size.getWidth(), size.getHeight());
        setBackground(color.getJColor());
        setLayout(null);
        resize(size);
        setIgnoreRepaint(true);
    }

    // Overrides java.awt.Component.getName()
    @Override
    public String getName() {
        return name;
    }

    protected void addView(final String key, final View view) {
        views.put(key, view);
        // Also smelly, inferring that all views are JPanels.
        add((JPanel)view);
    }

    protected void removeView(String key) {
        views.remove(key);
    }

    public View getView(final String key) {
        if (!views.containsKey(key)) {
            return null;
        }

        return views.get(key);
    }

    protected Size getScreenSize() {
        Dimension dim = super.getSize();
        return Size.of(dim.width, dim.height);
    }

    public void addViewGroup(String key, String...viewKeys) {
        viewGroup.computeIfAbsent(key, s -> new ArrayList<>());
        viewGroup.get(key).addAll(List.of(viewKeys));
    }

//    protected void removeViewGroup(Integer key) {
//        viewGroup.remove(key);
//    }

    public void displayViewGroup(final String key) {
        if (!viewGroup.containsKey(key)) {
            return;
        }

        if (key.equals(lastViewGroupSet)) {
            return;
        }

        views.values().forEach(view -> remove((JPanel)view));

        for (var viewKey : viewGroup.get(key)) {
            var targetView = getView(viewKey);
            if (targetView != null) {
                add((JPanel)targetView);
            }
        }

        lastViewGroupSet = key;

        draw();
    }

    public void resize(final Size parentSize) {
        setSize(parentSize.getWidth(), parentSize.getHeight());
        views.values().forEach(v -> v.resize(parentSize));
    }

    protected String getLastViewGroupSet() {
        return lastViewGroupSet;
    }

    public void draw() {
        repaint();
        views.values().forEach(View::draw);
    }
}
