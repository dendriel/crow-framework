package com.rozsa.crow.game;

import com.rozsa.crow.screen.UpdateListener;

import java.util.HashSet;

public class GameLoop {
    private static Thread gameLoop;
    private static boolean isStarted;
    private static boolean keepRunning;
    private static int gameLoopFPS = 60;
    private static HashSet<UpdateListener> onUpdateListeners;
    private static UpdateListener screenUpdateListener;

    static {
        onUpdateListeners = new HashSet<>();
        screenUpdateListener = () -> {};
    }

    private static void start() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        keepRunning = true;
        gameLoop = new Thread(GameLoop::updateLoop);
        gameLoop.start();
    }

    public static void terminate(long timeToWait) {
        if (!isStarted) {
            return;
        }

        keepRunning = false;
        try {
            gameLoop.join(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        while(keepRunning) {
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
