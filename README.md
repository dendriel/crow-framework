# Crow Framework / Crow Engine

Crow is a simple game engine to build 2D games in java.


## Development

### Build the lib

```shell
gradlew :engine:fatJar
```

### Publish
```shell
# Set Nexus username
$env:NEXUS_USER="username"
# Print username
$env:NEXUS_USER
# Set Nexus password
$env:NEXUS_PASSWORD="password"
# Print password
$env:NEXUS_PASSWORD

# Publish
gradlew :engine:publish
```

## Enable assertions while developing

Add this VM flag: `-ea`


## Nice To Have

### A GO component that allows to listen to input over the game object

This can be handled two ways:

#1 - The component itself is a ClickHandler that when a click is detected over the owner game object, has its callback
invoked to perform an action.

#2 - We have a ClickableComponent that when is clicked, the PointerInput generate events forwarding the object in which
the click was detected. This could be extended to provide events as: mouse in, mouse out, pointer press, release and
click.

Both ways will need a special implementation to observe pointer events and to check if the pointer event was over any
valid game object (the components which have the handler or are clickable).