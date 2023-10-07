package com.vrozsa.crowframework.shared.templates;

import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

@Builder
public record SpriteTemplate (
    String imageFile,
    int order,
    Offset offset,
    Scale scale,
    Size size,
    boolean enabled,
    boolean isFlipX,
    boolean isFlipY
) {

    public SpriteTemplate {
        enabled = true;
        offset = new Offset();
        scale = new Scale(1, 1);
    }

    public SpriteTemplate copy() {
        return new SpriteTemplate(
                imageFile,
                order,
                offset.clone(),
                scale.clone(),
                size.clone(),
                enabled,
                isFlipX,
                isFlipY
        );
    }
}
