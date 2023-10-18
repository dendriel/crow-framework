package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.sample.games.robinwood.GameObjectFactory;
import com.vrozsa.crowframework.shared.api.game.GameLoopAdder;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.patterns.AbstractCachedProvider;

public class EnemySpawner extends AbstractComponent {
    private final EnemiesFactory enemiesFactory;

    public EnemySpawner(GameObjectFactory goFactory, GameLoopAdder gameLoopAdder, GameObject target) {
        this.enemiesFactory = new EnemiesFactory(goFactory, gameLoopAdder, target);
    }

    @Override
    public void update() {
        super.update();

        // TODO: test the caching solution.
        // TODO: review this logic. just testing
        if (enemiesFactory.getActiveSize() > 1) {
            return;
        }

        var enemyWarriorController = enemiesFactory.get();
        enemyWarriorController.getPosition().setPosition(700, 100);

    }

    private static class EnemiesFactory extends AbstractCachedProvider<EnemyWarriorController> {
        private final GameObjectFactory goFactory;
        private final GameLoopAdder gameLoopAdder;
        private final GameObject target;

        public EnemiesFactory(GameObjectFactory goFactory, GameLoopAdder gameLoopAdder, GameObject target) {
            super(Integer.MAX_VALUE);
            this.goFactory = goFactory;
            this.gameLoopAdder = gameLoopAdder;
            this.target = target;
        }

        @Override
        protected EnemyWarriorController create() {
            var enemy = goFactory.createSkeletonWarrior(0, 0, target);
            gameLoopAdder.addGameObject(enemy.getGameObject());
            return enemy;
        }
    }
}
