package com.vrozsa.crowframework.sample.games.skeletonhunter;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;

import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BG_SCREEN_COLOR;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_STARTING_OFFSET;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getAudioData;

public class SkeletonHunterGame {

    public static void main(String[] args) {
        var game = new SkeletonHunterGame();
        game.start();
    }

    public void start() {
        var config = CrowEngineConfig.builder()
                .color(BG_SCREEN_COLOR)
                .title("Skeleton Hunter")
                .assetsPath("/assets/skeletonhunter")
                .showGizmos(false)
                .windowResizable(true)
                .build();

        var crowEngine = CrowEngine.create(config);
        crowEngine.getAudioManager().addAudioClipMetadata(getAudioData());

        var factory = new GameObjectFactory(crowEngine);

        var gameBoard = factory.createGameBoard();
        crowEngine.addGameObject(gameBoard);

        var heroGO = factory.createHero(HERO_STARTING_OFFSET);
        crowEngine.addGameObject(heroGO);

        var enemySpawner = factory.createEnemySpawner(heroGO.getPosition());
        crowEngine.addGameObject(enemySpawner);
    }
}
