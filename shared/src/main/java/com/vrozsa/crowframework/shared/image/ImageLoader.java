package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Helper class to load Images.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImageLoader {

    public static Image of(final BufferedImage bufferedImage) {
        return new ResizableImage(bufferedImage);
    }

    public static Image load(String filePath) {
        return load(filePath, filePath);
    }

    public static Image load(String filePath, String key) {
        try {
            if (filePath == null || filePath.isEmpty()) {
                return null;
            }

            Image resizableImage = ImageCache.get(key);
            if (resizableImage != null) {
                return resizableImage;
            }

            URL url = ResizableImage.class.getResource(filePath);
            BufferedImage bufferedImage = ImageIO.read(url);
            resizableImage = new ResizableImage(bufferedImage);
            ImageCache.add(key, resizableImage);

            return resizableImage;
        } catch (Exception ex) {
            System.out.printf("Failed to load image [%s]. Ex.: %s\n", filePath, ex);
        }

        // Pre image caching should not let this happen.
        return null;
    }
}
