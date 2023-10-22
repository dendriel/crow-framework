package com.vrozsa.crowframework.shared.api.game;

/**
 * Observes when a triggered animation has finished.
 */
@FunctionalInterface
public interface AnimationTriggerEndedObserver {
    /**
     * Called when the triggered animation has ended (has no more frames to render).
     */
    void triggerEnded();
}
