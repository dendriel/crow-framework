package com.vrozsa.crowframework.shared.image;

import com.vrozsa.crowframework.shared.api.screen.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static Map<String, Image> imageCache = new HashMap<>();

    public static Image get(String filePath) {
        if (imageCache.containsKey(filePath)) {
            return imageCache.get(filePath);
        }

        return null;
    }

    public static void add(String filePath, Image resizableImage) {
        imageCache.put(filePath, resizableImage);
    }
}
