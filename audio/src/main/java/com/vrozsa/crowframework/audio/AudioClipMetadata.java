package com.vrozsa.crowframework.audio;

import lombok.Builder;

/**
 * Metadata on how to load and play an audio clip.
 *
 * @param key audio clip key. Can be used to play back the audio clip (MUST be a unique key).
 * @param file name of the audio file to be loaded.
 * @param loop keep replaying the audio clip in a loop.
 */
@Builder
public record AudioClipMetadata(
    String key,
    String file,
    boolean loop
){}
