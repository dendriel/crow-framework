package com.vrozsa.crowframework.game.component.animation;


import com.vrozsa.crowframework.shared.attributes.Rect;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Template to used to configure animations.
 */
@Builder
public record AnimationTemplate(
   int layer,
   String name,
   Rect rect,
   List<String> spritesheets,
   Rect frameRect,
   long timeBetweenFrames,
   int firstFrame,
   boolean repeat,
   long intervalBeforeRepeating,
   boolean isActive
) {
    /**
     * Copies this animation template.
     * <p>
     *     Useful if we want to reuse a template and change just a few fields.
     * </p>
     * @return the AnimationTemplate copy.
     */
    public AnimationTemplate copy() {
        return new AnimationTemplate(
                layer,
                name,
                rect.clone(),
                new ArrayList<>(spritesheets),
                frameRect.clone(),
                timeBetweenFrames,
                firstFrame,
                repeat,
                intervalBeforeRepeating,
                isActive
        );
    }
}
