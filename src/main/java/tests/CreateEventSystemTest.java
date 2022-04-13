package tests;

import command.CreateTicketedEventCommand;
import command.RegisterEntertainmentProviderCommand;
import controller.Context;
import model.EventType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateEventSystemTest {

    private static void createAndLoginMovieProvider(Context context) {
        RegisterEntertainmentProviderCommand registerEntertainmentProviderCommand =
                new RegisterEntertainmentProviderCommand(
                        "Maguire Stans",
                        "Maguire Building, 15 Holyrood Park Rd.",
                        "willem.da-dripping@dailybugle.com",
                        "Patrick Bateman",
                        "p.bateman@pierce&pierce.com",
                        "Dorsia@9",
                        List.of("Jonah Jamieson", "Norman Osborne"),
                        List.of("j.jamieson@dailybugle.com", "norman@oscorp.org")
                );

        registerEntertainmentProviderCommand.execute(context);
    }

    @Test
    void getCinemaEvent() {
        Context context = new Context();
        createAndLoginMovieProvider(context);

        CreateTicketedEventCommand cmd = new CreateTicketedEventCommand(
                "Spider-Man 4: Ultimate Depression",
                EventType.Movie,
                200,
                12,
                false
        );
        cmd.execute(context);
        Long eventNum = cmd.getResult();
        assertEquals(1, eventNum);
    }
}
