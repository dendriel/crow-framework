package com.vrozsa.crowframework.game;

import com.vrozsa.crowframework.shared.api.game.GameLoop;
import com.vrozsa.crowframework.shared.api.game.UpdateListener;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.HashSet;
import java.util.Objects;

public class RunnableGameLoop implements GameLoop {
    private static LoggerService logger = LoggerService.of(RunnableGameLoop.class);

    private static RunnableGameLoop INSTANCE;

    private Thread gameLoop;
    private boolean isStarted;
    private volatile boolean keepRunning;
    private int gameLoopFPS;
    private long frameTime;
    private final HashSet<UpdateListener> onUpdateListeners;
    private final HashSet<UpdateListener> onLateUpdateListeners;
    private UpdateListener screenUpdateListener;
    private UpdateListener collisionUpdateListener;

    private long frame;

    private RunnableGameLoop() {
        gameLoopFPS = 60;
        frameTime = (long)(1000 / (float)gameLoopFPS);
        onUpdateListeners = new HashSet<>();
        onLateUpdateListeners = new HashSet<>();
        screenUpdateListener = () -> {};
        collisionUpdateListener = () -> {};
    }

    public static GameLoop get() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new RunnableGameLoop();
        }
        return INSTANCE;
    }

    @Override
    public void start() {
        if (isStarted) {
            return;
        }
        isStarted = true;
        keepRunning = true;
        gameLoop = new Thread(this::updateLoop);
        gameLoop.start();
    }

    @Override
    public void terminate(final long timeToWait) {
        if (!isStarted) {
            return;
        }

        keepRunning = false;
        try {
            gameLoop.join(timeToWait);
        } catch (InterruptedException e) {
            // should not get in here.
            e.printStackTrace();
        }

        isStarted = false;
    }

    public void setScreenUpdateListener(final UpdateListener listener) {
        screenUpdateListener = listener;
    }

    public void setCollisionUpdateListener(final UpdateListener listener) {
        collisionUpdateListener = listener;
    }

    public void setGameLoopFPS(final int value) {
        gameLoopFPS = value;
        frameTime = (long)(1000 / (float)gameLoopFPS);
    }

    public long getFrameTime() {
        return frameTime;
    }

    @Override
    public void addUpdateListener(final UpdateListener listener) {
        onUpdateListeners.add(listener);
    }

    @Override
    public void removeUpdateListener(final UpdateListener listener) {
        onUpdateListeners.remove(listener);
    }

    @Override
    public void addLateUpdateListener(UpdateListener listener) {
        onLateUpdateListeners.add(listener);
    }

    @Override
    public void removeLateUpdateListener(UpdateListener listener) {
        onLateUpdateListeners.remove(listener);
    }

    private void updateLoop() {
        while(keepRunning) {
//            logger.debug("Frame {0} has started.", frame);
            long startTime = System.currentTimeMillis();

            collisionUpdateListener.onUpdate();

            onUpdateListeners.forEach(UpdateListener::onUpdate);
            onLateUpdateListeners.forEach(UpdateListener::onUpdate);

            screenUpdateListener.onUpdate();

//            logger.debug("Frame {0} has ended.", frame);
            frame++;

            long timePassed = System.currentTimeMillis() - startTime;
            try {
                long sleepTime = Math.max(0, frameTime - timePassed);
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
