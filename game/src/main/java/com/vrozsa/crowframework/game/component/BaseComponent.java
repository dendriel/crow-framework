package com.vrozsa.crowframework.game.component;

import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.shared.api.game.GameObject;

public abstract class BaseComponent implements Component {
    // this should be final....
    protected GameObject gameObject;
    private boolean isEnabled;
    protected String name;
    protected long id;

    public BaseComponent() {
        this(true, "unnamed");
    }

    public BaseComponent(boolean isEnabled) {
        this(isEnabled, "unnamed");
    }

    public BaseComponent(boolean isEnabled, String name) {
        this.isEnabled = isEnabled;
        this.name = name;
    }

    protected <T extends Component> T getComponent(Class<T> kind) {
        return gameObject.getComponent(kind);
    }

    protected <T extends Component> T getComponent(Class<T> kind, String name) {
        return gameObject.getComponent(kind, name);
    }

    protected <T> T getService(Class<T> kind) {
        return gameObject.getService(kind);
    }

    @Override
    public void update() {}

    @Override
    public void lateUpdate() {}

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    // was a method from Component API, but it is not used outside game.
//    @Override
    public Position getPosition() {
        return gameObject.getComponent(Position.class);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isDisabled() {
        return !isEnabled;
    }

    @Override
    public void wrapUp() {
        // TODO Auto-generated method stub
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }
}
