package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.shared.api.game.GameLoop;
import com.vrozsa.crowframework.shared.api.game.UpdateListener;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Game loop created to use the screen without in stand-alone mode (i.e.: without a real game loop).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StandAloneGameLoop implements GameLoop, Runnable {
    private static final LoggerService logger = LoggerService.of(StandAloneGameLoop.class);
    private static final int gameLoopFPS;
    private static final long frameTime;
    private volatile UpdateListener screenUpdateListener;
    private volatile boolean keepRunning;
    private volatile Thread gameLoopThread;

    private static StandAloneGameLoop instance;

    static {
        gameLoopFPS = 60;
        frameTime = (long)(1000 / (float)gameLoopFPS);
    }

    public static StandAloneGameLoop getInstance() {
        if (Objects.isNull(instance)) {
            instance = new StandAloneGameLoop();
        }

        return instance;
    }

    @Override
    public void setScreenUpdateListener(final UpdateListener listener) {
        this.screenUpdateListener = listener;
        keepRunning = true;
        gameLoopThread = Thread.ofPlatform().start(this);
    }

    public void terminate() {
        logger.info("[run] loop is being interrupted...");
        gameLoopThread.interrupt();
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
