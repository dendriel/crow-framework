package com.vrozsa.crowframework.sample.games.skeletonhunter.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.sample.games.skeletonhunter.GameObjectFactory;
import com.vrozsa.crowframework.shared.api.game.GameLoopAdder;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import com.vrozsa.crowframework.shared.patterns.AbstractCachedProvider;

import java.util.Random;

import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SCREEN_SIZE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SPRITE_HEIGHT;

public class EnemySpawner extends AbstractComponent {
    private final EnemiesFactory enemiesFactory;
    private final PositionComponent target;

    private final Random random;

    // How many enemies will be active by 'distance' travelled.
    // Each array index represents a distance unit.
    // Dist 0 = 1 enemy; Dist 1 = 1 enemy; Dist 2 = 2 enemies; ...; Dist 5 = 4 enemies; until max dist/enemies
    private final int[] enemiesByDistance = new int[] {1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 6, 8, 8, 8, 10};

    public EnemySpawner(GameObjectFactory goFactory, GameLoopAdder gameLoopAdder, PositionComponent target) {
        this.enemiesFactory = new EnemiesFactory(goFactory, gameLoopAdder);
        this.target = target;

        random = new Random();
    }

    @Override
    public void update() {
        if (enemiesFactory.getActiveSize() >= getTargetAmountOfEnemies()) {
            return;
        }

        var spawnPointX = target.getAbsolutePosX() + SCREEN_SIZE.getWidth();
        var spawnPointY = random.nextInt(SPRITE_HEIGHT, SCREEN_SIZE.getHeight() - SPRITE_HEIGHT);

        var enemyWarriorController = enemiesFactory.get();
        enemyWarriorController.resetStatus();
        enemyWarriorController.activate(spawnPointX, spawnPointY, target);
    }

    private int distanceTravelled() {
        // 1 distance travelled = walked 1 board length.
        return target.getAbsolutePosX() / SCREEN_SIZE.getWidth();
    }

    private int getTargetAmountOfEnemies() {
        int distanceTravelled = distanceTravelled();

        if (distanceTravelled >= enemiesByDistance.length) {
            // Hero has reached the maximum enemies' setup.
            return enemiesByDistance[enemiesByDistance.length - 1];
        }

        return enemiesByDistance[distanceTravelled];
    }

    private static class EnemiesFactory extends AbstractCachedProvider<EnemyWarriorController> {
        private static final LoggerService logger = LoggerService.of(EnemiesFactory.class);

        private final GameObjectFactory goFactory;
        private final GameLoopAdder gameLoopAdder;

        public EnemiesFactory(GameObjectFactory goFactory, GameLoopAdder gameLoopAdder) {
            super(Integer.MAX_VALUE);
            this.goFactory = goFactory;
            this.gameLoopAdder = gameLoopAdder;
        }

        @Override
        protected EnemyWarriorController create() {
            var enemy = goFactory.createSkeletonWarrior(0, 0);
            gameLoopAdder.addGameObject(enemy.getGameObject());
            logger.debug("New EnemyWarrior created!");
            return enemy;
        }
    }
}
