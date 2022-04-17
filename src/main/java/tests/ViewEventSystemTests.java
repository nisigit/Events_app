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

import static org.testng.Assert.assertEquals;

public class ViewEventSystemTests {
    private Event event1, event2;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private void createUsers(Context context) {
        Consumer consumer = new Consumer(
                "Lovese Vents",
                "l.vents@wikipedia.org",
                "3",
                "Depression",
                "l.vents@paypal.com");
        ConsumerPreferences prefs = new ConsumerPreferences();
        prefs.socialDistancing = true;
        prefs.airFiltration = true;
        prefs.maxCapacity = 100;
        prefs.maxVenueSize = 100;

        consumer.setPreferences(prefs);

        EntertainmentProvider provider = new EntertainmentProvider(
                "Edinburgh University Indian Society",
                "indiansocediuni@gmail.com",
                "indiansoc.eusa@crypto.org",
                "Simran Punjabi",
                "indiansocediuni@gmail.com",
                "br0wn/t0wn",
                List.of("Isha Pawar", "Shivie Choudhry", "Diya Grace Jacob"),
                List.of("i.pawar@ed.ac.uk", "m.s.choudhry@ed.ac.uk", "dont-contact-me@im-so-tired.com"));

        context.getUserState().addUser(consumer);
        context.getUserState().addUser(provider);
    }

    private void addEvents(Context context) {
        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        CreateTicketedEventCommand event1 = new CreateTicketedEventCommand(
                "Pub Quiz",
                EventType.Theatre,
                100, 2.00, true);
        event1.execute(context);
        AddEventPerformanceCommand performance1 = new AddEventPerformanceCommand(event1.getResult(),
                "Cabaret Bar",
                LocalDateTime.now().plusMonths(8), LocalDateTime.now().plusMonths(8).plusHours(4),
                List.of("EUIS committee probably"),
                true, true, false, 100, 100);
        performance1.execute(context);
        this.event1 = context.getEventState().findEventByNumber(event1.getResult());

        CreateNonTicketedEventCommand event2 = new CreateNonTicketedEventCommand("Christmas Ball", EventType.Dance);
        event2.execute(context);
        AddEventPerformanceCommand performance2 = new AddEventPerformanceCommand(event2.getResult(),
                "Dynamic Earth, Holyrood Road",
                LocalDateTime.now().plusMonths(8), LocalDateTime.now().plusMonths(8).plusHours(5),
                new ArrayList<>(),
                true, true, false, 100, 100);
        performance2.execute(context);
        this.event2 = context.getEventState().findEventByNumber(event2.getResult());

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);
    }

    @Test
    void viewTicketedEvent() {
        Context context = new Context();
        createUsers(context);
        addEvents(context);

        ListEventsOnGivenDateCommand search = new ListEventsOnGivenDateCommand(false, true,
                LocalDateTime.now().plusMonths(8));
        search.execute(context);

        ArrayList<Event> eventList = (ArrayList<Event>) search.getResult();
        System.out.println(eventList);

        long event1 = eventList.get(0).getEventNumber();
        assertEquals(context.getEventState().findEventByNumber(event1).toString(), this.event1.toString(),
                "these events should display as the same string but don't");
    }

    @Test
    void viewNonTicketedEvent() {
        Context context = new Context();
        createUsers(context);
        addEvents(context);

        ListEventsOnGivenDateCommand search = new ListEventsOnGivenDateCommand(false, true,
                LocalDateTime.now().plusMonths(8));
        search.execute(context);

        ArrayList<Event> eventList = (ArrayList<Event>) search.getResult();

        long event2 = eventList.get(1).getEventNumber();
        assertEquals(context.getEventState().findEventByNumber(event2).toString(), this.event2.toString(),
                "these events should display as the same string but don't");
    }
}
