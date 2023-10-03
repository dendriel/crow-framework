package com.rozsa.screen.ui.listener;

import java.util.Objects;

public record UIEventListenerTuple<T>(T listener, Object state) {

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
