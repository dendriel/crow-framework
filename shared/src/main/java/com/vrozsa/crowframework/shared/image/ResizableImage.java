package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.attributes.Size;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ResizableImage implements Image {
    private HashMap<Size, BufferedImage> content;
    private BufferedImage defaultContent;

    public ResizableImage() {}

    public ResizableImage(final BufferedImage content) {
        defaultContent = content;

        initialize(content);
    }

    private void initialize(final BufferedImage defaultContent) {
        this.content = new HashMap<>();

        var key = Size.of(defaultContent.getWidth(), defaultContent.getHeight());
        this.content.put(key, defaultContent);
    }

    public BufferedImage getContent() {
        return defaultContent;
    }

    public BufferedImage getContent(int width, int height) {
        var key = Size.of(width, height);
        return content.computeIfAbsent(key, s ->
                Scalr.resize(defaultContent, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, width, height)
        );
    }
}
