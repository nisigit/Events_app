package tests;

import logging.Logger;
import model.*;
import org.junit.jupiter.api.*;

import state.EventState;

import model.EntertainmentProvider;
import static model.EventType.*;
import static org.testng.Assert.*;
import java.time.LocalDateTime;
import java.util.*;



public class TestEventState {
    EntertainmentProvider entertainmentProvider;
    EventState eventState;
    Event event;
    List<String> performerNames1;
    List<String> performerNames2;


    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @BeforeEach
    void setup() {
        this.entertainmentProvider = new EntertainmentProvider("Uni of Edinburgh", "Old College", "entProviderPaymentEmail@ed.ac.uk", "King Stuart", "KingStuart@ed.ac.uk", "password", List.of("Clark Barwick"), List.of("otheremails@ed.ac.uk"));
        this.event = new TicketedEvent(1, entertainmentProvider, "Super Bowl", Sports, 100, 50000);
        this.performerNames1 = List.of("Prince", "Katy Perry", "Bruno Mars");
        this.performerNames2 = List.of("New England Patriots", "Kansas City Chiefs");
        this.eventState = new EventState();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void testConstructors() {
        assertEquals(eventState.getAllEvents(), List.of(), "event list should be initialised as empty");
        EventState eventState1 = new EventState(eventState);
        assertEquals(eventState1, eventState, "should have the same values");
        assertNotSame(eventState1, eventState, "should be a DEEP copy, not shallow");
    }

    @DisplayName("Get all events")
    @Test
    void getAllEventsTest() {

        Event event0 = eventState.createNonTicketedEvent(entertainmentProvider, "Open Mic", Music);
        Event event1 = eventState.createTicketedEvent(entertainmentProvider, "Super Bowl", Sports, 100, 50000);
        List<Event> eventList = List.of(event0, event1);
        assertEquals(eventState.getAllEvents(), eventList, "this event list does not have the right events");
    }
    @DisplayName("Get Event by Number")
    @Test
    void findEventByNumberTest() {

        Event event0 = eventState.createNonTicketedEvent(entertainmentProvider, "Open Mic", Music);
        Event event1 = eventState.createTicketedEvent(entertainmentProvider, "Super Bowl", Sports, 100, 50000);
        Event event2 = eventState.createTicketedEvent(entertainmentProvider, "HairSpray", Theatre, 30, 3000);
        assertEquals(eventState.findEventByNumber(Integer.toUnsignedLong(1)), event0, "event not returned correctly");
        assertEquals(eventState.findEventByNumber(Integer.toUnsignedLong(2)), event1, "event not returned correctly");
        assertEquals(eventState.findEventByNumber(Integer.toUnsignedLong(3)), event2, "event not returned correctly");
        assertNull(eventState.findEventByNumber(Integer.toUnsignedLong(5)), "result should be null but isn't");

    }

    @DisplayName("Create non-ticketed event")
    @Test
    void createNonTicketedEventTest() {

        Event event0 = eventState.createNonTicketedEvent(entertainmentProvider, "Open Mic", Music);
        Event event3 = eventState.createNonTicketedEvent(entertainmentProvider, "Drive-In Movie", Movie);
        Event eventtest0 = new NonTicketedEvent(1, entertainmentProvider, "Open Mic", Music);
        Event eventtest3 = new NonTicketedEvent(2, entertainmentProvider, "Drive-In Movie", Movie);
        assertEquals(event0, eventtest0, "event not created correctly");
        assertEquals(event3, eventtest3, "event not created correctly");
    }

    @DisplayName("Create ticketed event")
    @Test
    void createTicketedEventTest() {

        Event event1 = eventState.createTicketedEvent(entertainmentProvider, "Super Bowl", Sports, 100, 50000);
        Event event2 = eventState.createTicketedEvent(entertainmentProvider, "HairSpray", Theatre, 30, 3000);
        Event eventtest1 = new TicketedEvent(1, entertainmentProvider, "Super Bowl", Sports, 100, 50000);
        Event eventtest2 = new TicketedEvent(2, entertainmentProvider, "HairSpray", Theatre, 30, 3000);
        assertEquals(event1, eventtest1, "event not created correctly");
        assertEquals(event2, eventtest2, "event not created correctly");

    }

    @DisplayName("Create event performance")
    @Test
    void createEventPerformanceTest() {

        EventPerformance eventperformance1 = eventState.createEventPerformance(event, "Some stadium in the US?", LocalDateTime.of(2019, 03, 28, 17, 00, 00, 000000), LocalDateTime.of(2019, 03, 28, 18, 00, 00, 000000), performerNames1, true, false, true, 50000, 60000);
        EventPerformance eventperformance2 = eventState.createEventPerformance(event, "Some other stadium?",  LocalDateTime.of(2019, 03, 28, 19, 00, 00, 000000),  LocalDateTime.of(2019, 03, 28, 22, 30, 00, 000000), performerNames2, true, true ,true, 60000, 70000);
        EventPerformance eventperformancetest1 = new EventPerformance(Integer.toUnsignedLong(1), event, "Some stadium in the US?", LocalDateTime.of(2019, 03, 28, 17, 00, 00, 000000), LocalDateTime.of(2019, 03, 28, 18, 00, 00, 000000), performerNames1, true, false, true, 50000, 60000);
        EventPerformance eventperformancetest2 = new EventPerformance(Integer.toUnsignedLong(2), event, "Some other stadium?",  LocalDateTime.of(2019, 03, 28, 19, 00, 00, 000000),  LocalDateTime.of(2019, 03, 28, 22, 30, 00, 000000), performerNames2, true, true ,true, 60000, 70000);
        assertEquals(eventperformance1, eventperformancetest1, "event performance not created correctly");
        assertEquals(eventperformance2, eventperformancetest2, "event performance not created correctly");

    }
}

