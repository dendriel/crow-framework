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