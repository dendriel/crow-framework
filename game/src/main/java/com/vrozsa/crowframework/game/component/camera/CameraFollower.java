package com.vrozsa.crowframework.game.component.camera;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.game.component.Position;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Camera follower allows to keep the camera centralized in relation to the target position.
 */
public class CameraFollower extends BaseComponent {
    // Target object to
    private final Position target;
    private final Offsetable cameraOffsetable;
    private final Offset offset;

    /**
     * @param target target position to follow.
     * @param cameraOffsetable interface to update the camera offset.
     * @param offset offset added to the current object position (can be used to center the position in the screen).
     */
    public CameraFollower(Position target, Offsetable cameraOffsetable, Offset offset) {
        this.target = target;
        this.cameraOffsetable = cameraOffsetable;
        this.offset = offset;
    }

    /**
     * Do on late update so the game object had a chance to move.
     */
    @Override
    public void lateUpdate() {
        super.lateUpdate();

        if (isDisabled() || getGameObject().isInactive()) {
            return;
        }

        // TODO: add pan control.
        // TODO: add Rect limits.
        var newOffset = target.getOffset().sub(offset);
        cameraOffsetable.setOffset(newOffset);
    }
}
