package com.vrozsa.crowframework.game.component;

import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.Position;

/**
 * This is the base class for components and can be extended to create custom components.
 * <p>
 *     Most of the time, when this class is extended, the only method that will be implemented is the update() method
 *     which contains the component's script.
 * </p>
 */
public abstract class AbstractComponent implements Component {
    // this should be final....
    protected GameObject gameObject;
    private boolean isEnabled;
    protected String name;
    protected long id;

    protected AbstractComponent() {
        this(true, "unnamed");
    }

    protected AbstractComponent(boolean isEnabled) {
        this(isEnabled, "unnamed");
    }

    protected AbstractComponent(boolean isEnabled, String name) {
        this.isEnabled = isEnabled;
        this.name = name;
    }

    protected <T> T getComponent(Class<T> kind) {
        return gameObject.getComponent(kind);
    }

    protected <T extends Component> T getComponent(Class<T> kind, String name) {
        return gameObject.getComponent(kind, name);
    }

    @Override
    public void wrapUp() {}

    @Override
    public void earlyUpdate() {}

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

    @Override
    public Position getPosition() {
        return gameObject.getComponent(PositionComponent.class);
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
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }
}
