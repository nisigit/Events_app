package tests;

import command.*;
import controller.Context;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CancelEventSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private void registerUsers(Context context) {
        EntertainmentProvider provider1 = new EntertainmentProvider(
                "Nitndeo",
                "Nippon",
                "doug.bowser@paypal.com",
                "Reggie Jr.",
                "regigigas@nitndeo.com",
                "sonicBae",
                new ArrayList<>(), new ArrayList<>());

        EntertainmentProvider provider2 = new EntertainmentProvider(
                "Edinburgh University Indian Society",
                "indiansocediuni@gmail.com",
                "indiansoc.eusa@crypto.org",
                "Simran Punjabi",
                "indiansocediuni@gmail.com",
                "br0wn/t0wn",
                List.of("Isha Pawar", "Shivie Choudhry", "Diya Grace Jacob"),
                List.of("i.pawar@ed.ac.uk", "m.s.choudhry@ed.ac.uk", "dont-contact-me@im-so-tired.com"));

        Consumer consumer = new Consumer(
                "Not Aprovider",
                "n.provider@noevents.com",
                "69696969696",
                "eventsn't",
                "n.provider@noevents.com");

        context.getUserState().addUser(provider1);
        context.getUserState().addUser(provider2);
        context.getUserState().addUser(consumer);
    }

    private Long createEvent1(Context context) {
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        CreateTicketedEventCommand event = new CreateTicketedEventCommand(
                "BOTW 2 release party", EventType.Dance,
                200, 12.00, true);
        event.execute(context);

        AddEventPerformanceCommand perform = new AddEventPerformanceCommand(event.getResult(),
                "test", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(1).plusHours(3), List.of("some person"),
                true, true, false, 100, 100);
        perform.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        login = new LoginCommand("n.provider@noevents.com", "eventsn't");
        login.execute(context);

        BookEventCommand bookEvent = new BookEventCommand(event.getResult(), perform.getResult().getPerformanceNumber(), 1);
        bookEvent.execute(context);

        logout.execute(context);

        return event.getResult();
    }

    private Long createEvent2(Context context) {
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        CreateNonTicketedEventCommand ntEvent = new CreateNonTicketedEventCommand("We Bought Sonic", EventType.Sports);
        ntEvent.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return ntEvent.getResult();
    }

    private Long createEvent3(Context context) {
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        CreateNonTicketedEventCommand ntEvent = new CreateNonTicketedEventCommand("Announcement: Activision owns Nintendo now", EventType.Theatre);
        ntEvent.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return ntEvent.getResult();
    }

    private boolean cancelEvent(Context context, long eventNumber, String organiserMessage) {
        CancelEventCommand cancelCommand = new CancelEventCommand(eventNumber, organiserMessage);
        cancelCommand.execute(context);
        return cancelCommand.getResult();
    }

    @Test
    void entProviderValidCancellation() {
        Context context = new Context();

        registerUsers(context);
        Long eventNumber = createEvent1(context);

        // Valid ent provider login
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        // cancelling event that actually exists, with a valid cancellation message
        assertTrue(cancelEvent(context, eventNumber, "I'm so sowwy uWu :3"));
        // check whether event status is actually logged as cancelled
        assertEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);

        // check whether booking status is logged as cancelled by provider correctly
        for (Booking b : context.getBookingState().findBookingsByEventNumber(eventNumber)) {
            assertEquals(b.getStatus(), BookingStatus.CancelledByProvider);
        }
    }

    @Test
    void entProviderNonExistentEventCancellation() {
        Context context = new Context();

        registerUsers(context);

        // Valid ent provider login
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        // cancelling non-existent event
        assertFalse(cancelEvent(context, 69, "lol"));
    }

    @Test
    void entProviderNullBlankMessageCancellation() {
        Context context = new Context();

        registerUsers(context);

        // Valid ent provider login
        LoginCommand login = new LoginCommand("regigigas@nitndeo.com", "sonicBae");
        login.execute(context);

        // cancelling existing event but with null / blank organiser message
        Long eventNumber = createEvent2(context);
        assertFalse(cancelEvent(context, eventNumber, null));
        assertFalse(cancelEvent(context, eventNumber, ""));

        // check if event status is NOT cancelled if organiser message is blank
        assertNotEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);
    }

    @Test
    void nonExistentUserCancellation() {
        Context context = new Context();

        registerUsers(context);

        LoginCommand login = new LoginCommand("fake@user.com", "IDontExistLol");
        login.execute(context);

        Long eventNumber = createEvent3(context);
        assertFalse(cancelEvent(context, eventNumber, "good news: activision doesn't own nintendo!"));
        // check if event status is NOT cancelled if user does not exist
        assertNotEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);
    }

    @Test
    void consumerCancellation() {
        Context context = new Context();

        registerUsers(context);

        // consumer tries to cancel event
        LoginCommand login = new LoginCommand("n.provider@noevents.com", "eventsn't");
        login.execute(context);

        Long eventNumber = createEvent3(context);
        assertFalse(cancelEvent(context, eventNumber, "i'm committing fraud"));
        // check if event status is NOT cancelled if user is not the organiser
        assertNotEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);
    }

    @Test
    void governmentRepCancellation() {
        Context context = new Context();

        registerUsers(context);

        LoginCommand login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        login.execute(context);

        Long eventNumber = createEvent3(context);
        assertFalse(cancelEvent(context, eventNumber, "i should not be able to do this"));
        // check if event status is NOT cancelled if user is not the organiser
        assertNotEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);
    }

    @Test
    void nonOrganiserCancellation() {
        Context context = new Context();

        registerUsers(context);

        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        Long eventNumber = createEvent3(context);
        assertFalse(cancelEvent(context, eventNumber, "i should not be able to do this"));
        // check if event status is NOT cancelled if user is not the organiser
        assertNotEquals(context.getEventState().findEventByNumber(eventNumber).getStatus(), EventStatus.CANCELLED);
    }
}
