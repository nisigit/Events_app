package tests;

import command.RegisterConsumerCommand;
import controller.Context;
import logging.Logger;
import model.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConsumerSystemTest {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void registerConsumerTest() {
        Context context = new Context();
        RegisterConsumerCommand registerConsumerCommand = new RegisterConsumerCommand(
                "Joe Root",
                "joe.root@ecb.co.uk",
                "-",
                "win_the_ashes",
                "payments@specsavers.co.uk"
        );

        registerConsumerCommand.execute(context);
        Consumer consumer = registerConsumerCommand.getResult();

        assertTrue(context.getUserState().getAllUsers().containsValue(consumer), "consumer has not been added to UserState");
        assertSame(context.getUserState().getCurrentUser(), consumer, "the current user has not been set correctly");
    }

}
