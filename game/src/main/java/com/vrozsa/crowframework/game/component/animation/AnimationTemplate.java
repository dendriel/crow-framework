package com.vrozsa.crowframework.game.component.animation;


import com.vrozsa.crowframework.shared.attributes.Rect;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Template to used to configure animations.
 */
@Data
public class AnimationTemplate {
    private Rect rect;
    private List<String> spritesheets;
    private Rect frameRect;
    private long timeBetweenFrames;
    private int firstFrame;
    private boolean repeat;
    private long intervalBeforeRepeating;
    private boolean isActive;

    public AnimationTemplate copy() {
        var copy = new AnimationTemplate();

        copy.rect = rect.clone();
        copy.spritesheets = new ArrayList<>(spritesheets);
        copy.frameRect = frameRect.clone();
        copy.timeBetweenFrames = timeBetweenFrames;
        copy.firstFrame = firstFrame;
        copy.repeat = repeat;
        copy.intervalBeforeRepeating = intervalBeforeRepeating;

        return copy;
    }
}
