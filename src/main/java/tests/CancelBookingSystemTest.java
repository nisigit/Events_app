package tests;

import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import state.BookingState;

import static org.testng.Assert.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CancelBookingSystemTest {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private EntertainmentProvider createEntProvider(Context context) {
        EntertainmentProvider entertainmentProvider =
                new EntertainmentProvider("A provider",
                        "A place",
                        "AProviderEmail@somewhere.com",
                        "ARep",
                        "Rep'sEmail@somewhere.com",
                        "Imapassword",
                        List.of("Some other reps"),
                        List.of("OtherRepsEmails@somewhere.com"));
        context.getUserState().addUser(entertainmentProvider);
        return entertainmentProvider;
    }

    private User loginEntProvider(Context context) {
        LoginCommand login = new LoginCommand("Rep'sEmail@somewhere.com", "Imapassword");
        login.execute(context);

        return login.getResult();
    }

    private Consumer createConsumer(Context context) {
        Consumer consumer =
                new Consumer(
                        "Consumer",
                        "ConsumersEmail@somewhere.com",
                        "randomPhoneNumber",
                        "ConsumerPassword",
                        "ConsumerPaymentEmail@somewhere.com");
        context.getUserState().addUser(consumer);
        return consumer;
    }

    private User loginConsumer(Context context) {
        LoginCommand login = new LoginCommand("ConsumersEmail@somewhere.com", "ConsumerPassword");
        login.execute(context);

        return login.getResult();
    }

    private void logOut(Context context) {
        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);
    }

    private Long createEvent1(Context context) {
        CreateTicketedEventCommand createEventCommand = new CreateTicketedEventCommand("Lecturer Concert",
                EventType.Theatre,
                200,
                20.00,
                true);

        createEventCommand.execute(context);

        return createEventCommand.getResult();
    }

    private EventPerformance createPerformanceEvent1(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                1,
                "Edinburgh Castle",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(2),
                List.of("John Lonely, Chris Sangwin"),
                true,
                true,
                true,
                100,
                2000);

        addEventPerformanceCommand.execute(context);

        return addEventPerformanceCommand.getResult();
    }

    private EventPerformance createPerformance2Event1(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                1,
                "Edinburgh Castle",
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(8),
                List.of("Nikola Popvic, Clark Barwick"),
                true,
                true,
                true,
                1000,
                20000);

        addEventPerformanceCommand.execute(context);

        return addEventPerformanceCommand.getResult();
    }

    private Long createEvent2(Context context) {
        CreateTicketedEventCommand createEventCommand = new CreateTicketedEventCommand("MEADOWS PARTY",
                EventType.Music,
                20000,
                20.00,
                true);

        createEventCommand.execute(context);

        return createEventCommand.getResult();
    }

    private EventPerformance createPerformanceEvent2(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                2,
                "Meadows",
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusDays(10).plusHours(13),
                List.of("Boris Johnson, Donald Trump"),
                true,
                true,
                true,
                10000,
                200000);

        addEventPerformanceCommand.execute(context);

        return addEventPerformanceCommand.getResult();
    }

    private long bookEvent1Performance1(Context context) {
        BookEventCommand bookEventCommand = new BookEventCommand(1,
                1,
                10);

        bookEventCommand.execute(context);

        return bookEventCommand.getResult();
    }

    private long bookEvent1Performance2(Context context) {
        BookEventCommand bookEventCommand = new BookEventCommand(1,
                2,
                1);

        bookEventCommand.execute(context);

        return bookEventCommand.getResult();
    }

    private long bookEvent2Performance1(Context context) {
        BookEventCommand bookEventCommand = new BookEventCommand(2,
                3,
                1);

        bookEventCommand.execute(context);

        return bookEventCommand.getResult();
    }

    @Test
    void cancelEventTest() {
        Context context = new Context();

        EntertainmentProvider createdEntProvider = createEntProvider(context);
        User entProvider = loginEntProvider(context);
        assertEquals(entProvider, createdEntProvider);
        assertEquals(context.getUserState().getCurrentUser(), createdEntProvider);

        long createEvent1Result = createEvent1(context);
        assertEquals(createEvent1Result, 1);
        assertEquals(context.getEventState().getAllEvents().size(), 1);

        long createEvent2Result = createEvent2(context);
        assertEquals(createEvent2Result, 2);
        assertEquals(context.getEventState().getAllEvents().size(), 2);

        EventPerformance createEvent1Performance1 = createPerformanceEvent1(context);
        EventPerformance createEvent1Performance2 = createPerformance2Event1(context);
        EventPerformance createEvent2Performance1 = createPerformanceEvent2(context);
        assertEquals(context.getEventState().findEventByNumber(1).getPerformanceByNumber(1), createEvent1Performance1);
        assertEquals(context.getEventState().findEventByNumber(1).getPerformanceByNumber(2), createEvent1Performance2);
        assertEquals(context.getEventState().findEventByNumber(2).getPerformanceByNumber(3), createEvent2Performance1);
        assertEquals(context.getEventState().findEventByNumber(1).getPerformances().size(), 2);
        assertEquals(context.getEventState().findEventByNumber(2).getPerformances().size(), 1);

        logOut(context);

        Consumer createdConsumer = createConsumer(context);
        User consumer = loginConsumer(context);
        assertEquals(consumer, createdConsumer);
        assertEquals(context.getUserState().getCurrentUser(), createdConsumer);

        long bookEvent1Performance1Result = bookEvent1Performance1(context);
        long bookEvent1Performance2Result = bookEvent1Performance2(context);
        long bookEvent2Performance1Result = bookEvent2Performance1(context);
        BookingState bookingState = (BookingState) context.getBookingState();
        assertNotNull(bookingState.findBookingByNumber(bookEvent1Performance1Result));
        assertNotNull(bookingState.findBookingByNumber(bookEvent1Performance2Result));
        assertNotNull(bookingState.findBookingByNumber(bookEvent2Performance1Result));

        CancelBookingCommand cancelBookingCommand1 = new CancelBookingCommand(bookEvent1Performance1Result);
        CancelBookingCommand cancelBookingCommand2 = new CancelBookingCommand(bookEvent1Performance2Result);
        CancelBookingCommand cancelBookingCommand3 = new CancelBookingCommand(bookEvent2Performance1Result);

        cancelBookingCommand1.execute(context);
        cancelBookingCommand2.execute(context);
        cancelBookingCommand3.execute(context);

        assertTrue(cancelBookingCommand1.getResult());
        assertFalse(cancelBookingCommand2.getResult());
        assertFalse(cancelBookingCommand3.getResult());
        assertNull(context.getBookingState().findBookingByNumber(bookEvent1Performance1Result));
        assertNotNull(bookingState.findBookingByNumber(bookEvent1Performance2Result));
        assertNotNull(bookingState.findBookingByNumber(bookEvent2Performance1Result));
    }
}
