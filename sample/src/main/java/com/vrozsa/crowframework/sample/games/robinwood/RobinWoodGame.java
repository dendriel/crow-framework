package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;

import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.BG_SCREEN_COLOR;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.HERO_STARTING_OFFSET;

public class RobinWoodGame {

    public static void main(String[] args) {
        var robinWoodGame = new RobinWoodGame();
        robinWoodGame.start();
    }

    public void start() {
        var config = CrowEngineConfig.builder()
                .color(BG_SCREEN_COLOR)
                .title("Robin Wood")
                .showGizmos(false)
                .build();

        var crowEngine = CrowEngine.create(config);

        var factory = new GameObjectFactory(crowEngine);

        var gameBoard = factory.createGameBoard();
        crowEngine.addGameObject(gameBoard);

        var heroGO = factory.createHero(HERO_STARTING_OFFSET);
        crowEngine.addGameObject(heroGO);

        var enemySpawner = factory.createEnemySpawner(heroGO);
        crowEngine.addGameObject(enemySpawner);

//        var skeletonWarriorGO = factory.createSkeletonWarrior(700, 100, heroGO);
//        crowEngine.addGameObject(skeletonWarriorGO);


//        crowEngine.addGameObject(factory.createSkeletonWarrior(200, 200, heroGO));
//        crowEngine.addGameObject(factory.createSkeletonWarrior(500, 300, heroGO));
//        crowEngine.addGameObject(factory.createSkeletonWarrior(450, 300, heroGO));
//        crowEngine.addGameObject(factory.createSkeletonWarrior(300, 500, heroGO));
    }
}
