package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.api.game.Identifiable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SfxData implements Identifiable {
    private long id;
    private String name;
    private String soundFile;
    private int length;
}
