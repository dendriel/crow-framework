package com.rozsa.crow.game;

import com.rozsa.crow.screen.UpdateListener;

import java.util.HashSet;

public class GameLoop {
    private static boolean isStarted;
    private static int gameLoopFPS = 60;
    private static HashSet<UpdateListener> onUpdateListeners;
    private static UpdateListener screenUpdateListener;

    static {
        onUpdateListeners = new HashSet<>();
        screenUpdateListener = () -> {};
    }

    private static void start() {
        if (!isStarted) {
            isStarted = true;
            new Thread(GameLoop::updateLoop).start();
        }
    }

    public static void setScreenUpdateListener(UpdateListener listener) {
        screenUpdateListener = listener;
        start();
    }

    public static void setGameLoopFPS(int value) {
        gameLoopFPS = value;
    }

    public static void addOnUpdateListener(UpdateListener listener) {
        onUpdateListeners.add(listener);
        start();
    }

    public static void removeOnUpdateListener(UpdateListener listener) {
        onUpdateListeners.remove(listener);
    }

    private static void updateLoop() {
        long frameTime = (long)(1000 / (float)gameLoopFPS);
        while(true) {
            long startTime = System.currentTimeMillis();

            onUpdateListeners.forEach(UpdateListener::onUpdate);
            screenUpdateListener.onUpdate();

            long timePassed = System.currentTimeMillis() - startTime;
            try {
                long sleepTime = Math.max(0, frameTime - timePassed);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
