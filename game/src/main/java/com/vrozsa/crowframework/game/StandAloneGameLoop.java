package com.vrozsa.crowframework.game;

import com.vrozsa.crowframework.shared.api.game.UpdateListener;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import com.vrozsa.crowframework.shared.time.TimeUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Game loop created to use the screen without in stand-alone mode (i.e.: without a real game loop).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StandAloneGameLoop implements GameLoop, Runnable {
    private static final int DEFAULT_FPS = 60;
    private static final LoggerService logger = LoggerService.of(StandAloneGameLoop.class);
    private static final int gameLoopFPS;
    private static final long frameTime;

    // making the reference volatile is exactly what we want here.
    private volatile UpdateListener screenUpdateListener;
    private boolean isStarted;
    private volatile boolean keepRunning;
    private Thread gameLoop;

    private static StandAloneGameLoop INSTANCE;

    static {
        gameLoopFPS = DEFAULT_FPS;
        frameTime = TimeUtils.calculateFrameTime(gameLoopFPS);
    }

    /**
     * Get the stand-alone game loop instance.
     * @return the game-loop instance.
     */
    public static GameLoop get() {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new StandAloneGameLoop();
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
        gameLoop = Thread.ofPlatform().start(this);
    }

    @Override
    public void terminate(long timeToWait) {
        logger.info("[run] loop is being interrupted...");
        gameLoop.interrupt();

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

    @Override
    public void addUpdateListener(UpdateListener listener) {}

    @Override
    public void removeUpdateListener(UpdateListener listener) {}

    @Override
    public void addLateUpdateListener(UpdateListener listener) {}

    @Override
    public void removeLateUpdateListener(UpdateListener listener) {}

    @Override
    public void addEarlyUpdateListener(UpdateListener listener) {

    }

    @Override
    public void removeEarlyUpdateListener(UpdateListener listener) {

    }

    @Override
    public void setCollisionUpdateListener(UpdateListener listener) {}

    /**
     * Sets the screen update listener.
     * @param listener screen's update listener.
     */
    @Override
    public void setScreenUpdateListener(final UpdateListener listener) {
        this.screenUpdateListener = listener;
    }

    @Override
    public void run() {
        logger.info("[run] loop has started!");

        while(keepRunning) {
            long startTime = System.currentTimeMillis();

            screenUpdateListener.onUpdate();

            long timePassed = System.currentTimeMillis() - startTime;
            try {
                long sleepTime = Math.max(0, frameTime - timePassed);
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        screenUpdateListener = null;
        logger.info("[run] loop has finished!");
    }
}
