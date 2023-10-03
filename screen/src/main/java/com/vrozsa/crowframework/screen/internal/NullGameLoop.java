package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.shared.api.game.GameLoop;
import com.vrozsa.crowframework.shared.api.game.UpdateListener;
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
