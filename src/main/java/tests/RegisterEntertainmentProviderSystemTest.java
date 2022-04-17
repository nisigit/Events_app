package tests;

import command.RegisterEntertainmentProviderCommand;
import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.testng.Assert.*;

public class RegisterEntertainmentProviderSystemTest {

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
    void registerEntProviderTest() {
        Context context = new Context();
        RegisterEntertainmentProviderCommand registerEntertainmentProviderCommand = new RegisterEntertainmentProviderCommand(
            "Maguire Stans",
            "Maguire Building, 15 Holyrood Park Rd",
                "willem-da-dripping@oscorp.org",
                "Patrick Bateman",
                "p.bateman@pierce&pierce.com",
                "Dorsia@9",
                List.of("Jonah Jamieson", "Norman Osborn"),
                List.of("j.jamieson@dailybugle.com", "norman@oscord.com")
        );

        registerEntertainmentProviderCommand.execute(context);
        EntertainmentProvider entertainmentProvider = registerEntertainmentProviderCommand.getResult();

        assertTrue(context.getUserState().getAllUsers().containsValue(entertainmentProvider),
                "entertainment provider has not been added to UserState");
        assertSame(context.getUserState().getCurrentUser(), entertainmentProvider,
                "the current user has not been set correctly");
    }
}
