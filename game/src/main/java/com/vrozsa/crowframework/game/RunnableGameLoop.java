package com.vrozsa.crowframework.game;


import com.vrozsa.crowframework.shared.api.game.GameLoop;
import com.vrozsa.crowframework.shared.api.game.UpdateListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RunnableGameLoop implements GameLoop {
    private static Thread gameLoop;
    private static boolean isStarted;
    private static boolean keepRunning;
    private static int gameLoopFPS;
    private static long frameTime;
    private static HashSet<UpdateListener> onUpdateListeners;
    private static UpdateListener screenUpdateListener;

    static {
        gameLoopFPS = 60;
        frameTime = (long)(1000 / (float)gameLoopFPS);
        onUpdateListeners = new HashSet<>();
        screenUpdateListener = () -> {};
    }

    private static void start() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        keepRunning = true;
        gameLoop = new Thread(RunnableGameLoop::updateLoop);
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

    /**
     * TODO: make this a singleton and stop using statics.
     */
    public static GameLoop create() {
        return new RunnableGameLoop();
    }

    public void setScreenUpdateListener(UpdateListener listener) {
        screenUpdateListener = listener;
        start();
    }

    public static void setGameLoopFPS(int value) {
        gameLoopFPS = value;
        frameTime = (long)(1000 / (float)gameLoopFPS);
    }

    public static long getFrameTime() {
        return frameTime;
    }

    public static void addOnUpdateListener(UpdateListener listener) {
        onUpdateListeners.add(listener);
        start();
    }

    public static void removeOnUpdateListener(UpdateListener listener) {
        onUpdateListeners.remove(listener);
    }

    private static void updateLoop() {
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
