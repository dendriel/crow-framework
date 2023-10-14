package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.game.component.camera.CameraFollower;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.attributes.Rect;

/**
 * Restricts the allowed movement area as the game progress.
 */
public class MovementAreaUpdater extends BaseComponent {
    private final Offsetable camera;
    private CameraFollower cameraFollower;
    private CharacterDriver characterDriver;


    /**
     * @param camera camera offset component.
     */
    public MovementAreaUpdater(final Offsetable camera) {
        this.camera = camera;
    }

    @Override
    public void wrapUp() {
        super.wrapUp();
        cameraFollower = getComponent(CameraFollower.class);
        assert cameraFollower != null : "MovementAreaUpdater requires a CameraFollower";

        characterDriver = getComponent(CharacterDriver.class);
        assert characterDriver != null : "MovementAreaUpdater requires a characterDriver";
    }

    @Override
    public void lateUpdate() {
        super.update();

        int newLimitX = camera.getOffset().getX();

        Rect followBox = cameraFollower.getFollowBox();
        followBox.setX(newLimitX);
        cameraFollower.setFollowBox(followBox);

        characterDriver.setLeftLimitX(newLimitX);
    }
}
