package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.shared.api.input.InputKey;

public class SimpleEngineSetup {

    public static void main(String[] args) {

        CrowEngine crowEngine = CrowEngine.create();

        InputManager inputManager = crowEngine.getInputManager();

        inputManager.readUntil(InputKey.ENTER);
    }
}
