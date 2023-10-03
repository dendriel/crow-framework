package com.vrozsa.crowframework.shared.api.screen;

import java.awt.image.BufferedImage;

/**
 * Holds image content and metadata.
 */
public interface Image {

    /**
     * Get the default image.
     * @return the default image.
     */
    BufferedImage getContent();

    /**
     * Get the image with the target width and height. Will resize the image on the fly if target size is not cached yet.
     * @param width target image width.
     * @param height target image height.
     * @return the image with the requested size.
     */
    BufferedImage getContent(int width, int height);
}
