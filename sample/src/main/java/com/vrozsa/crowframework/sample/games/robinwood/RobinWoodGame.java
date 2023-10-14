package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;

import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.HERO_STARTING_OFFSET;

public class RobinWoodGame {

    public static void main(String[] args) {

        var crowEngine = CrowEngine.create();
        var factory = new GameObjectFactory(crowEngine);

        var bgGOA = factory.createBackground();
        crowEngine.addGameObject(bgGOA);

        var heroGO = factory.createHero(HERO_STARTING_OFFSET);
        crowEngine.addGameObject(heroGO);



//        var animator = heroGO.getComponent(AnimatedRenderer.class);
    }
}
