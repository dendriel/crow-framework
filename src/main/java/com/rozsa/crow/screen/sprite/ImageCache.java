package com.rozsa.crow.screen.sprite;

import java.util.HashMap;
import java.util.Map;

class ImageCache {
    private static Map<String, Image> imageCache = new HashMap<>();

    public static Image get(String filePath) {
        if (imageCache.containsKey(filePath)) {
            return imageCache.get(filePath);
        }

        return null;
    }

    public static void add(String filePath, Image image) {
        imageCache.put(filePath, image);
    }
}
