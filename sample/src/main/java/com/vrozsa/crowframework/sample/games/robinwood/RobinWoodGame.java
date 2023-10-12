package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;

public class RobinWoodGame {

    public static void main(String[] args) {

        var crowEngine = CrowEngine.create();
        var factory = new GameObjectFactory(crowEngine);

        var bgGOA = factory.createBackground();
        crowEngine.addGameObject(bgGOA);

        var heroGO = factory.createHero(300, 300);
        crowEngine.addGameObject(heroGO);



//        var animator = heroGO.getComponent(AnimatedRenderer.class);
    }
}
