# Crow Framework
## Audio Module

> Handles music and sound effects.

Built over the `javax.sound.sampled.*` package.

- **Audio Handlers** - main element that knows how to deal with audio clips metadata and playing.
- **Audio Clips** - represents the audio clips and wraps around the native implementation. Knows how to load and handles the
native clips.
- **Audio Clip Metadata** - tells how audio clips can be loaded and played.