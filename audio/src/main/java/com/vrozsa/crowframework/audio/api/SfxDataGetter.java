package com.vrozsa.crowframework.audio.api;

import com.vrozsa.crowframework.audio.SfxData;

import java.util.Optional;

/**
 * Retrieves metadata about sound effects.
 */
@FunctionalInterface
public interface SfxDataGetter {
    /**
     * Gets SFX data by its name.
     * @param name SFX name.
     * @return the SFX data if found; empty if not found.
     */
    Optional<SfxData> get(String name);
}
