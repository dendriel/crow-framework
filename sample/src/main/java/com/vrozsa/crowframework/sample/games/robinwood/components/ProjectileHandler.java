package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.Direction;
import com.vrozsa.crowframework.shared.api.game.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ProjectileHandler extends AbstractComponent {

    // Projectiles supplier by type.
    private final Map<String, Supplier<GameObject>> projectilesSupplier;

    // Projectiles by type
    private Map<String, List<GameObject>> projectiles;

    public ProjectileHandler(final Map<String, Supplier<GameObject>> projectilesSupplier) {
        this.projectilesSupplier = projectilesSupplier;
        projectiles = new HashMap<>();
    }

    public void spawnProjectile(final String type, final int x, final int y, final Direction direction, final GameObject owner) {
        var projectile = getProjectile(type);

        var projectileController = projectile.getComponent(ProjectileController.class);
        assert projectileController != null : "Projectile has no controller!";

        projectileController.activate(x, y, direction, owner);
    }

    private GameObject getProjectile(final String type) {
        var projectilesOfType = getProjectilesOfType(type);

        return projectilesOfType.stream()
                .filter(GameObject::isInactive)
                .findFirst()
                .orElseGet(() -> {
                    GameObject projectile = createProjectile(type);
                    projectilesOfType.add(projectile);
                    return projectile;
                });
    }

    private List<GameObject> getProjectilesOfType(final String type) {
        return projectiles.computeIfAbsent(type, t -> new ArrayList<>());
    }

    private GameObject createProjectile(final String type) {
        Supplier<GameObject> supplier = projectilesSupplier.get(type);
        assert supplier != null : String.format("Could not find a supplier for projectile type %s!", type);
        return supplier.get();
    }
}
