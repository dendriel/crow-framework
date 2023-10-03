package com.rozsa.screen;

import com.rozsa.shared.api.game.GameLoop;
import com.rozsa.shared.api.game.UpdateListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NullGameLoop implements GameLoop {
    @Override
    public void setScreenUpdateListener(UpdateListener listener) {

    }
}
