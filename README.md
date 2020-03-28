# Crow Framework
Crow framework is a Java-based Game development Framework. It provides the basic features to easy game development in Java.

# Features

- Screen handling;
  - UI components;
  - Gameplay renderer.
- Input handling;
- Sound handling;
- Component-based gameplay entities. 

## Screen

Screen related features follows the hierarchy bellow:

- UIComponent - basic UI component that is draw and allows user interaction;
- View - a composition of UIComponents. Holds and draws UIComponents;
- Screen - manages compositions of views and allows to display groups of views together as they
are one entity;
- ScreenHandler - holds a list of screens available in the game.


Basic screen setup:
```Java
// Setup screen handler.
ScreenHandlerConfig config = new ScreenHandlerConfig();
config.setTitle("My Game Title");
Size screenSize = new Size(800, 600);
config.setSize(screenSize);
ScreenHandler<ScreenType> screenHandler = new ScreenHandler<>(ScreenType.class, config);

// Setup a custom screen and add it into ScreenHandler.
SplashScreen splashScreen = new SplashScreen(screenSize);
screenHandler.add(ScreenType.SPLASH, splashScreen);

// Display the scren.
screenHandler.setOnlyVisible(ScreenType.SPLASH, true);
```
