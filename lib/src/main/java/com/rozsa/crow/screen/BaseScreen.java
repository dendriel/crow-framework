package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseScreen<TKey, TKeyGroup> extends JPanel {
    private Map<TKey, BaseView> views;

    private Map<TKeyGroup, TKey[]> viewGroup;

    private TKeyGroup lastViewGroupSet;

    public BaseScreen(Size size) {
        this(size, Color.blue());
    }

    public BaseScreen(Size size, Color bgColor) {
        views = new HashMap<>();
        viewGroup = new HashMap<>();

        setup(size, bgColor);
    }

    private void setup(Size size, Color color) {
        setSize(size.getWidth(), size.getHeight());
        setBackground(color.getJColor());
        setLayout(null);
        updateScreenSize(size);
        setIgnoreRepaint(true);
    }

    protected void addView(TKey key, BaseView view) {
        views.put(key, view);
        add(view);
    }

    protected void removeView(TKey key) {
        views.remove(key);
    }

    protected BaseView getView(TKey key) {
        if (!views.containsKey(key)) {
            return null;
        }

        return views.get(key);
    }

    protected void addViewGroup(TKeyGroup key, TKey...viewKeys) {
        viewGroup.put(key, viewKeys);
    }

    protected void removeViewGroup(Integer key) {
        viewGroup.remove(key);
    }

    protected void displayViewGroup(TKeyGroup key) {
        if (!viewGroup.containsKey(key)) {
            return;
        }

        if (lastViewGroupSet == key) {
            return;
        }

        views.values().forEach(this::remove);
        for (TKey viewKey : viewGroup.get(key)) {
            BaseView targetView = getView(viewKey);
            if (targetView != null) {
                add(targetView);
            }
        }

        lastViewGroupSet = key;

        draw();
    }

    public void updateScreenSize(Size parentSize) {
        setSize(parentSize.getWidth(), parentSize.getHeight());
        views.values().forEach(v -> v.updateScreenSize(parentSize));
    }

    protected TKeyGroup getLastViewGroupSet() {
        return lastViewGroupSet;
    }

    protected void draw() {
        repaint();
        views.values().forEach(BaseView::draw);
    }
}
