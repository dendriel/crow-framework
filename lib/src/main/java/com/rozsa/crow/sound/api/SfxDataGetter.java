package com.rozsa.crow.sound.api;

import com.rozsa.crow.sound.SfxData;

public interface SfxDataGetter {
    SfxData getSfxData(String fileName);

    String getSfxPath(String fileName);
}
