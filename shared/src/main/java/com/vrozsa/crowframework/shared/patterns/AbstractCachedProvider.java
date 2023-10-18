package com.vrozsa.crowframework.shared.patterns;

import com.vrozsa.crowframework.shared.api.game.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The cached factory is typed component provider that generates new components or reuse inactive components when
 * available.
 * <p>
 *     As the components are always attached to GameObjects, the factory considers components available to reuse if their
 * GameObjects are inactive.</p>
 * <p>
 *     If the instances are to be used by multiple activation/deactivation, this provider may not be appropriate. The
 * intended use of this provider is to handle instances with a linear time line (like projectiles, enemies, etc). Is to
 * say, the instance will be returned active from the cache, will do what it have to do, and when inactivated by the
 * game, it will automatically be made available in the cache for reuse.</p>
 *
 * *Implements the Factory Method pattern.
 *
 * @param <T> the type of component to provide.
 */
public abstract class AbstractCachedProvider<T extends Component> {
    // Searching through the list will yield log(n) complexity.
    // TODO: we could improve this by tracking when the game objects become inactive (i.e. add a listener to each GO before
    // returning it).
    private final List<T> elements;
    private final int maxSize;

    /**
     * @param maxSize the maximum amount of instances in the cache.
     */
    protected AbstractCachedProvider(int maxSize) {
        this.maxSize = maxSize;
        elements = new ArrayList<>();
    }

    /**
     * Gets an available instance of T. The underlying game-object will be activated before being returned. When the
     * GameObject become inactive again, it will be automatically 'return' to the available pool in the cache.
     * @return the instance of T.
     */
    public T get() {
        return elements.stream()
                .filter(c -> c.getGameObject().isInactive())
                .findFirst()
                .orElseGet(() -> {
                    if (elements.size() == maxSize) {
                        return null;
                    }
                    var newComponent = create();
                    elements.add(newComponent);
                    newComponent.getGameObject().setActive(true);
                    return newComponent;
                });
    }

    /**
     * Counts the amount of active elements in the cache.
     * @return the amount of active elements in the cache.
     */
    public long getActiveSize() {
        return elements.stream()
                .filter(c -> c.getGameObject().isActive())
                .count();
    }


    /**
     * Provide a new instance of T.
     * <p>The instance should be inactive.The instance also have to be added to the game screen to be rendered in the
     * game loop.</p>
     * @return the new instance created.
     */
    protected abstract T create();
}
