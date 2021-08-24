package com.rozsa.crow.screen.sprite;

import com.rozsa.crow.screen.attributes.Size;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Image {
    private HashMap<Size, BufferedImage> content;
    private BufferedImage defaultContent;

    public Image() {}

    public Image(BufferedImage content) {
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

    public static Image load(String filePath) {
        return load(filePath, filePath);
    }

    public static Image load(String filePath, String key) {
        try {
            if (filePath == null || filePath.isEmpty()) {
                return null;
            }

            Image image = ImageCache.get(key);
            if (image != null) {
                return image;
            }

            URL url = Image.class.getResource(filePath);
            BufferedImage bufferedImage = ImageIO.read(url);
            image = new Image(bufferedImage);
            ImageCache.add(key, image);

            return image;
        } catch (Exception ex) {
            System.out.printf("Failed to load image [%s]. Ex.: %s\n", filePath, ex);
        }

        // Pre image caching should not let this happen.
        return null;
    }
}
