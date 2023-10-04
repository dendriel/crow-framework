package com.vrozsa.crowframework.audio.api;

import com.vrozsa.crowframework.audio.SfxData;

public interface SfxDataGetter {
    SfxData getSfxData(String fileName);

    String getSfxPath(String fileName);
}
