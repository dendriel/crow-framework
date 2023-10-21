package com.vrozsa.crowframework.sample.games.skeletonhunter.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.screen.ui.UILabel;

/**
 * Keeps the player status in sync with the HUD elements.
 */
public class PlayerHUD extends AbstractComponent {
    private final UILabel lifesLabel;
    private final UILabel scoreLabel;
    private CharacterStatus status;

    public PlayerHUD(UILabel lifesLabel, UILabel scoreLabel) {
        this.lifesLabel = lifesLabel;
        this.scoreLabel = scoreLabel;
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        status = getComponent(CharacterStatus.class);
        assert status != null : "PlayerHUD requires a CharacterStatus component";
    }

    @Override
    public void update() {
        super.update();

        lifesLabel.setText(String.format("Lifes: %d", status.getLife()));

        scoreLabel.setText(String.format("Score: %d", status.getScore()));
    }
}
