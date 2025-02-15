package com.vrozsa.crowframework.shared.image;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Helper class that pre-loads all images inside a directory.
 */
// TODO: review this.
public final class ImagePreLoader {
    public static final String PNG_EXT = ".png";

    private final String imagesPath;

    private final String allowedExt;

    public ImagePreLoader(String imagesPath, String allowedExt) {
        this.imagesPath = imagesPath;
        this.allowedExt = allowedExt;
    }

    /**
     * Cache sprite's buffered images beforehand.
     */
    public void load() {
        try {
            for (final String fileName : getFileListing(getClass(), imagesPath, new HashSet<>())) {
//                System.out.println("Cache image: " + fileName);
                ImageLoader.load(fileName);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // TODO: review.
    // https://brixomatic.wordpress.com/2013/01/27/listing-subdirectory-in-jar-file/
    static <T extends Collection<? super String>> T getFileListing(
            final Class<?> clazz, final String path, final T result) throws URISyntaxException,
            IOException {
        URL dirURL = clazz.getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            result.addAll(Arrays.asList(new File(dirURL.toURI()).list()));
            return result;
        }
        if (dirURL == null) {
            // In case of a jar file, we can't actually find a directory.
            // Have to assume the same jar as clazz.
            final String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getResource(me);
        }
        if (dirURL.getProtocol().equals("jar")) { /* A JAR path */
            // strip out only the JAR file
            final String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            final JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            final Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries in jar
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                final int pathIndex = name.lastIndexOf(path);
                if (pathIndex > 0) {
                    final String nameWithPath = name.substring(name.lastIndexOf(path));
                    result.add(nameWithPath.substring(path.length()));
                }
            }
            jar.close();
            return result;
        }
        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }
}
