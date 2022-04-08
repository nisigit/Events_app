package tests;

import command.*;
import controller.Controller;
import model.EventPerformance;
import model.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookEventSystemTest {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    private void registerUsers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Aper Son",
                "human@being.com",
                "0000000",
                "IAmAGuy",
                "humanpayment@money.com"));
        controller.runCommand(new LogoutCommand());

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Marvel Studios",
                "New York probably",
                "kebinfeeg@bing.com",
                "Kabro Fug",
                "kebinfeeg@bing.com",
                "synder4eva",
                new ArrayList<>(), new ArrayList<>()));
        controller.runCommand(new LogoutCommand());
    }

    private EventPerformance createEventWithPerformance(Controller controller) {
        controller.runCommand(new LoginCommand("kebinfeeg@bing.com", "synder4eva"));
        CreateTicketedEventCommand movie = new CreateTicketedEventCommand(
                "Spider-Man 4: God Pls Can Zendaya Be My GF Again #dard",
                EventType.Movie,
                400,
                5.00,
                true);
        controller.runCommand(movie);
        long eventNumber = movie.getResult();

        AddEventPerformanceCommand moviePerformance = new AddEventPerformanceCommand(
                eventNumber,
                "Vue",
                LocalDateTime.now().plusYears(1),
                LocalDateTime.now().plusYears(1).plusHours(3),
                List.of("Tom Holland, Zendaya, Jared Leto, Tom Hardy, Robert Pattinson"),
                true, true, false, 400, 400);
        controller.runCommand(moviePerformance);
        controller.runCommand(new LogoutCommand());
        return moviePerformance.getResult();
    }

    private Long bookEvent(Controller controller, long eventNumber,
                           long performanceNumber, int nrTicketsRequested) {
        BookEventCommand book = new BookEventCommand(eventNumber, performanceNumber, nrTicketsRequested);
        controller.runCommand(book);
        return book.getResult();
    }

    @Test
    void bookEventTest() {
        Controller controller = new Controller();
        registerUsers(controller);
        EventPerformance performance = createEventWithPerformance(controller);

        // valid user login
        controller.runCommand(new LoginCommand("human@being.com", "IAmAGuy"));

        // valid booking attempt
        Long bookingNumber = bookEvent(controller, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);
        assertNotNull(bookingNumber);

        // tries to book event that does not exist
        bookingNumber = bookEvent(controller, 32421341, 3412, 902);
        assertNull(bookingNumber);

        // what if you try to book more tickets than the event has available?
        bookingNumber = bookEvent(controller, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 500);
        assertNull(bookingNumber);

        // completely invalid user login
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand("fake@user.com", "IDontExistLol"));

        // what happens if this invalid user tries to book an event?
        bookingNumber = bookEvent(controller, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);
        assertNull(bookingNumber);

        // what happens if someone who's not a consumer tries to book an event?
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand("kebinfeeg@bing.com", "synder4eva"));

        bookingNumber = bookEvent(controller, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);
        assertNull(bookingNumber);
    }
}
