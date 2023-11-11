package com.vrozsa.crowframework.game;

import com.vrozsa.crowframework.shared.api.game.UpdateListener;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import com.vrozsa.crowframework.shared.time.TimeUtils;

import java.util.HashSet;
import java.util.Objects;

/**
 * Implements the default game-loop.
 */
final class CrowGameLoop implements GameLoop {
    private static final int DEFAULT_FPS = 60;
    private static LoggerService logger = LoggerService.of(CrowGameLoop.class);

    private static CrowGameLoop INSTANCE;

    private Thread gameLoop;
    private boolean isStarted;
    private volatile boolean keepRunning;
    private int gameLoopFPS;
    private long frameTime;
    private final HashSet<UpdateListener> onUpdateListeners;
    private final HashSet<UpdateListener> onLateUpdateListeners;
    private final HashSet<UpdateListener> onEarlyUpdateListeners;
    private UpdateListener screenUpdateListener;
    private UpdateListener collisionUpdateListener;

    private long frame;

    private CrowGameLoop() {
        gameLoopFPS = DEFAULT_FPS;
        frameTime = TimeUtils.calculateFrameTime(gameLoopFPS);
        onUpdateListeners = new HashSet<>();
        onLateUpdateListeners = new HashSet<>();
        onEarlyUpdateListeners = new HashSet<>();
        screenUpdateListener = () -> {};
        collisionUpdateListener = () -> {};
    }

    public static GameLoop get() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new CrowGameLoop();
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
        gameLoop = Thread.ofPlatform().start(this::updateLoop);
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

    @Override
    public void addEarlyUpdateListener(UpdateListener listener) {
        onEarlyUpdateListeners.add(listener);
    }

    @Override
    public void removeEarlyUpdateListener(UpdateListener listener) {
        onEarlyUpdateListeners.remove(listener);
    }

    private void updateLoop() {
        while(keepRunning) {
//            logger.debug("Frame {0} has started.", frame);
            long startTime = System.currentTimeMillis();

            collisionUpdateListener.onUpdate();

            onEarlyUpdateListeners.forEach(UpdateListener::onUpdate);
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
