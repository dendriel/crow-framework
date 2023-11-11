package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Make available the offset of an object (without exposing the object).
 */
public interface OffsetGetter {
    /**
     * Gets the offset of an element.
     * @return the offset.
     */
    Offset getOffset();
}
