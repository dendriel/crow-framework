package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;

/**
 * Helper class to be used to implement collision handling logic.
 */
public abstract class BaseCollisionHandler extends BaseComponent {

    /**
     * Handle collisions between objects.
     * @param source source game object.
     * @param target target object in which the source has collided.
     */
    protected abstract void handle(GameObject source, GameObject target);
}
