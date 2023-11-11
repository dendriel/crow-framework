package com.vrozsa.crowframework.sample.games.skeletonhunter.components;


import com.vrozsa.crowframework.game.component.AbstractComponent;

/**
 * Holds character status related data.
 */
public class CharacterStatus extends AbstractComponent {
    private int maxLife;
    private int currLife;
    private int score;
    private int scoreValue;

    public CharacterStatus(int maxLife) {
        this(maxLife, 0);
    }

    /**
     * @param maxLife maximum amount of life of this character (also the starting life value).
     * @param scoreValue score given if this character is defeated.
     */
    public CharacterStatus(int maxLife, int scoreValue) {
        this.maxLife = maxLife;
        this.currLife = maxLife;
        this.scoreValue = scoreValue;
    }

    public void reset() {
        currLife = maxLife;
    }

    /**
     * Removes life from status.
     * @param value the amount of life to be removed.
     * @return true if has no life left; false otherwise.
     */
    public boolean removeLife(final int value) {
        currLife = Math.max(0, currLife - value);

        if (currLife == 0) {
            gameObject.setActive(false);
            return true;
        }

        return false;
    }

    public void addLife(final int value) {
        currLife = Math.min(maxLife, currLife + value);
    }

    public int getLife() {
        return currLife;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        this.score += value;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public void update() {
        // no op.
    }
}
