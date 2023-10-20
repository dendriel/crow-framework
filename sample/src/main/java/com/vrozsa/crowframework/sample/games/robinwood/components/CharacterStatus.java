package com.vrozsa.crowframework.sample.games.robinwood.components;


import com.vrozsa.crowframework.game.component.AbstractComponent;

/**
 * Holds character status related data.
 */
public class CharacterStatus extends AbstractComponent {
    private int maxLife;

    private int currLife;

    public CharacterStatus(int maxLife) {
        this.maxLife = maxLife;
        this.currLife = maxLife;
    }

    public void reset() {
        currLife = maxLife;
    }

    public void removeLife(final int value) {
        currLife = Math.max(0, currLife - value);

        if (currLife == 0) {
            gameObject.setActive(false);
        }
    }

    public void addLife(final int value) {
        currLife = Math.min(maxLife, currLife + value);
    }
}
