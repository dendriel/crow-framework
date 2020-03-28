package com.rozsa;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;

public class Main {
    // testing purposes
    public static void main(String[] args) {

        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle("Main Test Window");
        Size screenSize = new Size(800, 600);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setFullscreen(true);


        ScreenHandler<ScreenType> screen = new ScreenHandler<>(ScreenType.class, config);
        System.out.println("LooooL");

        screen.setVisible(true);
        System.out.println("w: " + screen.getWidth() + " h: " + screen.getHeight());


//        Size screenSize = new Size(800, 600);
//        Size screenSize = new Size(1920, 1080);
        Image.setImagesPath("/images");
        SplashScreen splashScreen = new SplashScreen(screenSize);
        screen.add(ScreenType.SPLASH, splashScreen);
        screen.setOnlyVisible(ScreenType.SPLASH, true);
    }
}

