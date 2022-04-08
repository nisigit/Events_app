package tests;

import command.CreateTicketedEventCommand;
import command.RegisterEntertainmentProviderCommand;
import controller.Controller;
import model.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateEventSystemTest {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    private static void createAndLoginMovieProvider(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Maguire Stans",
                "Maguire Building, 15 Holyrood Park Rd.",
                "willem.da-dripping@dailybugle.com",
                "Patrick Bateman",
                "p.bateman@pierce&pierce.com",
                "Dorsia@9",
                List.of("Jonah Jamieson", "Norman Osborne"),
                List.of("j.jamieson@dailybugle.com", "norman@oscorp.org")
        ));

    }

    @Test
    void getCinemaEvent() {
        Controller controller = new Controller();
        createAndLoginMovieProvider(controller);

        CreateTicketedEventCommand cmd = new CreateTicketedEventCommand(
                "Spider-Man 4: Ultimate Depression",
                EventType.Movie,
                200,
                12,
                false
        );
        controller.runCommand(cmd);
        Long eventNum = cmd.getResult();
        assertEquals(1, eventNum);
    }
}
