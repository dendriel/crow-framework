package com.rozsa.crow.screen.ui.listener;

import java.util.Objects;

public class UIEventListenerTuple<T> {
    private final T listener;
    private final Object state;

    public UIEventListenerTuple(T listener, Object state) {
        this.listener = listener;
        this.state = state;
    }

    public T getListener() {
        return listener;
    }

    public Object getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIEventListenerTuple that = (UIEventListenerTuple) o;
        return Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listener);
    }
}
