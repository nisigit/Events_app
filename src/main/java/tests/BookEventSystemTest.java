package tests;

import command.*;
import controller.Context;
import controller.Controller;
import model.Consumer;
import model.EntertainmentProvider;
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
import java.util.Locale;

public class BookEventSystemTest {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    private void registerUsers(Context context) {
        Consumer consumer = new Consumer(
                "Aper Son",
                "human@being.com",
                "0000000",
                "IAmAGuy",
                "humanpayment@money.com");

        EntertainmentProvider provider = new EntertainmentProvider(
                "Marvel Studios",
                "New York probably",
                "kebinfeeg@bing.com",
                "Kabro Fug",
                "kebinfeeg@bing.com",
                "synder4eva",
                new ArrayList<>(), new ArrayList<>());

        context.getUserState().addUser(consumer);
        context.getUserState().addUser(provider);
    }

    private EventPerformance createEventWithPerformance(Context context) {
        LoginCommand login = new LoginCommand("kebinfeeg@bing.com", "synder4eva");
        login.execute(context);

        CreateTicketedEventCommand movie = new CreateTicketedEventCommand(
                "Spider-Man 4: God Pls Can Zendaya Be My GF Again #dard",
                EventType.Movie,
                400,
                5.00,
                true);
        movie.execute(context);
        long eventNumber = movie.getResult();

        AddEventPerformanceCommand moviePerformance = new AddEventPerformanceCommand(
                eventNumber,
                "Vue",
                LocalDateTime.now().plusYears(1),
                LocalDateTime.now().plusYears(1).plusHours(3),
                List.of("Tom Holland, Zendaya, Jared Leto, Tom Hardy, Robert Pattinson"),
                true, true, false, 400, 400);
        moviePerformance.execute(context);
        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return moviePerformance.getResult();
    }

    private Long bookEvent(Context context, long eventNumber,
                           long performanceNumber, int nrTicketsRequested) {
        BookEventCommand book = new BookEventCommand(eventNumber, performanceNumber, nrTicketsRequested);
        book.execute(context);
        return book.getResult();
    }

    @Test
    void bookEventTest() {
        Context context = new Context();
        registerUsers(context);
        EventPerformance performance = createEventWithPerformance(context);
        LogoutCommand logout = new LogoutCommand();

        // valid user login
        LoginCommand login = new LoginCommand("human@being.com", "IAmAGuy");
        login.execute(context);

        // valid booking attempt
        Long bookingNumber = bookEvent(context, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);

        // check if number was returned properly, and then if booking was logged in the booking state
        assertNotNull(bookingNumber);
        assertNotNull(context.getBookingState().findBookingByNumber(bookingNumber));

        // tries to book event that does not exist
        bookingNumber = bookEvent(context, 32421341, 3412, 902);
        assertNull(bookingNumber);

        // what if you try to book more tickets than the event has available?
        bookingNumber = bookEvent(context, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 500);
        assertNull(bookingNumber);

        logout.execute(context);

        // completely invalid user login
        login = new LoginCommand("fake@user.com", "IDontExistLol");
        login.execute(context);

        // what happens if this invalid user tries to book an event?
        bookingNumber = bookEvent(context, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);
        assertNull(bookingNumber);

        logout.execute(context);

        // what happens if someone who's not a consumer tries to book an event?
        login = new LoginCommand("kebinfeeg@bing.com", "synder4eva");
        login.execute(context);

        bookingNumber = bookEvent(context, performance.getEvent().getEventNumber(), performance.getPerformanceNumber(), 2);
        assertNull(bookingNumber);

        //TODO: Add case for govt rep trying to book an event
    }
}
