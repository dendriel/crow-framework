package com.vrozsa.crowframework.sample.input.readkeys;

import com.vrozsa.crowframework.BufferedInputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.logger.LoggerService;

public class Main {

    private static final LoggerService logger = LoggerService.of("ReadKeys Sample");

    public static void main(String[] args) {

        // TODO: not working right know. Needs a screen.

        BufferedInputHandler inputHandler = new BufferedInputHandler();

        logger.debug("Reading keys..");

        InputKey next = inputHandler.getNext();

        logger.debug("Key pressed is.. %s", next);
    }
}
