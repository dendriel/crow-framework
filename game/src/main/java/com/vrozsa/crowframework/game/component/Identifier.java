package com.vrozsa.crowframework.game.component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Identifier component is used to add a custom identifier to a game-object.
 * <p>
 *     Can ensure uniqueness of identifiers if using groups.
 *     WARNING: not thread-safe! Should be used before starting the game-loop and from inside the game-loop.
 * </p>
 */
public final class Identifier extends AbstractComponent {
    /**
     * Used to detect duplicated identifiers.
     */
    private static final HashMap<String, Set<String>> identifiers = new HashMap<>();

    private Identifier(final String name) {
        this.name = name;
    }

    /**
     * Creates a new Identifier component.
     * <p>
     *     This creation method doesn't ensure uniqueness.
     * </p>
     * @param name the identifier of the component.
     * @return the new Identifier.
     */
    public static Identifier create(final String name) {
        return new Identifier(name);
    }

    /**
     * Creates a new Identifier component, ensuring that it is unique within the specified group.
     * @param group identifier group. This is just a subdivision if we may have duplicate identifiers in different
     *              contexts.
     * @param name identifier name.
     * @return the identifier, if specified a unique ID for the target group; an empty optional, if the identifier is
     * duplicated within the specified group.
     */
    public static Optional<Identifier> create(final String group, final String name) {
        var targetGroup = identifiers.computeIfAbsent(group, k -> new HashSet<>());

        if (targetGroup.contains(name)) {
            return Optional.empty();
        }

        targetGroup.add(name);
        return Optional.of(new Identifier(name));
    }

    @Override
    public void update() {
        // no op.
    }
}
