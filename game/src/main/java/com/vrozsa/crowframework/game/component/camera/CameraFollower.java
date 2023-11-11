package com.vrozsa.crowframework.game.component.camera;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.game.component.PositionComponent;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;

import java.util.Objects;

/**
 * Camera follower allows to keep the camera centralized in relation to the target position.
 */
public final class CameraFollower extends AbstractComponent {
    // Target object to
    private final PositionComponent target;
    private final Offsetable cameraOffsetable;
    private final Offset offset;
    private Rect followBox;

    private CameraFollower(PositionComponent target, Offsetable cameraOffsetable, Offset offset, Rect followBox) {
        this.target = target;
        this.cameraOffsetable = cameraOffsetable;
        this.offset = offset;
        this.followBox = followBox;
    }

    /**
     * Create a new CameraFollower component.
     * @param target target position to follow.
     * @param cameraOffsetable interface to update the camera offset.
     * @param offset offset added to the current object position (can be used to center the position in the screen).
     * @param followBox (optional) the boundaries in which the camera can follow the character. If out of the follow box
     *                  the camera will stop following.
     */
    public static CameraFollower create(PositionComponent target, Offsetable cameraOffsetable, Offset offset, Rect followBox) {
        return new CameraFollower(target, cameraOffsetable, offset, followBox);
    }

    @Override
    public void update() {
        // no op.
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
        var newOffset = target.getOffset().sub(offset);

        if (Objects.isNull(followBox)) {
            cameraOffsetable.setOffset(newOffset);
            return;
        }

        // cast width to long before the sum, so it won't overflow.
        if (newOffset.getX() < followBox.getX() || newOffset.getX() > followBox.getX() + (long)followBox.getWidth()) {
            newOffset.setX(cameraOffsetable.getOffset().getX());
        }

        if (newOffset.getY() < followBox.getY() || newOffset.getY() > followBox.getY() + (long)followBox.getHeight()) {
            newOffset.setY(cameraOffsetable.getOffset().getY());
        }

        cameraOffsetable.setOffset(newOffset);
    }

    /**
     * Update the follow box.
     * @param newFollowBox the new allowed area in which the target will be followed by the camera.
     */
    public void setFollowBox(final Rect newFollowBox) {
        followBox = newFollowBox.clone();
    }

    /**
     * Get the following box.
     * @return the following box.
     */
    public Rect getFollowBox() {
        return followBox.clone();
    }
}
