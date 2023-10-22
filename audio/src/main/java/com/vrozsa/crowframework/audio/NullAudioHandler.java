package com.vrozsa.crowframework.audio;

import static java.util.Objects.isNull;

/**
 * Null implementation of AudioPlayer that plays no sounds and can be used to replace the real implementation in certain
 * situations.
 * <p>
 *     See 'Null Object' behavioral pattern.
 * </p>
 */
final class NullAudioHandler implements AudioHandler {
    // Implemented as a singleton because this implementation don't have a state, so we don't need multiple instances.
    private static NullAudioHandler instance;

    private NullAudioHandler() {}

    /**
     * Gets a NullAudioPlayer.
     * @return the NullAudioPlayer instance.
     */
    public static AudioHandler get() {
        if (isNull(instance)) {
            instance = new NullAudioHandler();
        }

        return instance;
    }

    @Override
    public void play(String key) {
        // Null object implementation.
    }

    @Override
    public void playSync(String key) {
        // Null object implementation.
    }
}
