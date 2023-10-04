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

    public ResizableImage(BufferedImage content) {
        defaultContent = content;

        initialize(content);
    }

    private void initialize(BufferedImage defaultContent) {
        this.content = new HashMap<>();

        Size key = new Size(defaultContent.getWidth(), defaultContent.getHeight());
        this.content.put(key, defaultContent);
    }

    public BufferedImage getContent() {
        return defaultContent;
    }

    public BufferedImage getContent(int width, int height) {
        Size key = new Size(width, height);
        BufferedImage bufferedImage = content.get(key);

        if (bufferedImage == null) {
            bufferedImage = Scalr.resize(defaultContent, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, width, height);
            content.put(key, bufferedImage);
        }

        return bufferedImage;
    }
}
