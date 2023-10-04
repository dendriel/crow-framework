package com.vrozsa.crowframework.shared.templates;

import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Data;

@Data
public class SpriteTemplate {
    private String imageFile;
    private int order;
    private Offset offset;
    private Scale scale;
    private Size size;
    private boolean enabled;
    private boolean isFlipX;
    private boolean isFlipY;

    public SpriteTemplate() {
        offset = new Offset();
        scale = new Scale(1, 1);
    }

    public SpriteTemplate copy() {
        var copy = new SpriteTemplate();

        copy.imageFile = imageFile;
        copy.order = order;
        copy.offset = offset.clone();
        copy.scale = scale.clone();
        copy.enabled = enabled;
        copy.isFlipX = isFlipX;
        copy.isFlipY = isFlipY;
        copy.size = size.clone();

        return copy;
    }
}
