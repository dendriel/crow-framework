package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Make available the offset of an object (without exposing the object).
 */
interface OffsetGetter {
    Offset getOffset();
}
