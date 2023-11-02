package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Helper class to load Images from resources.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImageLoader {
    private static final LoggerService logger = LoggerService.of(ImageLoader.class);

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

            var resizableImage = ImageCache.get(key);
            if (resizableImage != null) {
                return resizableImage;
            }

            var url = ResizableImage.class.getResource(filePath);
            var bufferedImage = ImageIO.read(url);
            resizableImage = new ResizableImage(bufferedImage);
            ImageCache.add(key, resizableImage);

            return resizableImage;
        }
        catch (Exception ex) {
            logger.error("Failed to load image [{0}].\n{1}", filePath, ex);
        }

        // Pre-image caching should not let this happen.
        return null;
    }
}
