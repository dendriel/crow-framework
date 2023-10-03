package com.vrozsa.crowframework.sample.input.readkeys;

import com.vrozsa.crowframework.BufferedInputHandler;
import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.builder.ScreenHandlerFactory;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;

public class Main {

    private static final LoggerService logger = LoggerService.of("ReadKeys Sample");

    public static void main(String[] args) {
        BufferedInputHandler inputHandler = new BufferedInputHandler();

        ScreenHandler<ScreenType> screenHandler = ScreenHandlerFactory.createWithSimpleScreen(BACKGROUND_IMAGE_FILE, inputHandler);

        logger.debug("Reading keys..");

        InputKey next;
        do {
            next = inputHandler.getNext();
            logger.debug("Key pressed is.. %s", next);
        } while (next != InputKey.ESCAPE);

        screenHandler.exit();
    }
}
