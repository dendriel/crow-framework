package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Allows getting and setting the offset of an element.
 */
public interface Offsetable {
    /**
     * Sets the offset
      * @param offset offset value to be set.
     */
    void setOffset(Offset offset);

    /**
     * Gets the offset.
     * @return the offset of the element.
     */
    Offset getOffset();
}
