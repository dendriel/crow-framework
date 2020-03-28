package com.rozsa.crow.screen.sprite;

import com.rozsa.crow.screen.attributes.Size;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Image {
    private static String imagesPath;

    public static void setImagesPath(String path) {
        imagesPath = path;
    }

    private HashMap<Size, BufferedImage> content;

    private BufferedImage defaultContent;

    private final String name;

    public Image(BufferedImage content) {
        this(content, "");
    }

    public Image(BufferedImage content, String name) {
        this.name = name;
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
            bufferedImage = Scalr.resize(defaultContent, Scalr.Method.ULTRA_QUALITY, width, height);
            content.put(key, bufferedImage);
        }

        return bufferedImage;
    }

    public String getName() {
        return name;
    }

    private static String formatPath(String imageFile) {
        return String.format("%s/%s", imagesPath, imageFile);
    }

    public static Image load(String imageFile) {
        try {
            if (imageFile == null || imageFile.isEmpty()) {
                return null;
            }

            String filePath = formatPath(imageFile);

            Image image = ImageCache.get(filePath);
            if (image != null) {
                return image;
            }

            URL url = Image.class.getResource(filePath);
            BufferedImage bufferedImage = ImageIO.read(url);
            image = new Image(bufferedImage, filePath);
            ImageCache.add(filePath, image);

            return image;
        } catch (IOException ex) {
            System.out.printf("Failed to load image [%s]. Ex.: %s\n", imageFile, ex);
        }

        // Pre image caching should not let this happen.
        return null;
    }
}
