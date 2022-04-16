package tests;

import command.*;
import controller.*;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import state.BookingState;
import static org.testng.Assert.*;
import java.time.LocalDateTime;
import java.util.*;
import static org.testng.Assert.assertEquals;

public class SearchForEventsSystemTests {

    private Context context;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @BeforeEach
    void setup() {
        this.context = new Context();
        createEntProvider(context);
        createConsumers(context);
        loginEntProvider(context);
        createEvent1(context);
        createEvent2(context);
        createPerformance1Event1(context);
        createPerformance2Event1(context);
        createPerformance1Event2(context);
        createPerformance2Event2(context);
        logOut(context);
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private void createEntProvider(Context context) {
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
    }

    private void loginEntProvider(Context context) {
        LoginCommand login = new LoginCommand("Rep'sEmail@somewhere.com", "Imapassword");
        login.execute(context);
    }

    private void createConsumers(Context context) {
        Consumer consumer1 =
                new Consumer(
                        "Consumer",
                        "Consumer1sEmail@somewhere.com",
                        "randomPhoneNumber",
                        "ConsumerPassword",
                        "ConsumerPaymentEmail@somewhere.com");

        Consumer consumer2 =
                new Consumer(
                        "Consumer2",
                        "Consumer2sEmail@somewhere.com",
                        "HateRandomPhoneNumber",
                        "InterestingPassword",
                        "Idon'tpay@somewhere.com");

        Consumer consumer3 =
                new Consumer(
                        "Consumer3",
                        "Consumer3sEmail@somewhere.com",
                        "911",
                        "password",
                        "Rich@somewhere.com");
        context.getUserState().addUser(consumer1);
        context.getUserState().addUser(consumer2);
        context.getUserState().addUser(consumer3);
    }

    private ConsumerPreferences createConsumerPreferences1() {
        return new ConsumerPreferences(true, false, false, 10000, 200000);
    }

    private ConsumerPreferences createConsumerPreferences2() {
        return new ConsumerPreferences(true, false, true, 100, 2000);
    }

    private ConsumerPreferences createConsumerPreferences3() {
        return new ConsumerPreferences(false, false, false, 100, 2000);
    }

    private void loginConsumer1(Context context) {
        LoginCommand login = new LoginCommand("Consumer1sEmail@somewhere.com", "ConsumerPassword");
        login.execute(context);
    }

    private void loginConsumer2(Context context) {
        LoginCommand login = new LoginCommand("Consumer2sEmail@somewhere.com", "InterestingPassword");
        login.execute(context);
    }

    private void loginConsumer3(Context context) {
        LoginCommand login = new LoginCommand("Consumer3sEmail@somewhere.com", "password");
        login.execute(context);
    }

    private void logOut(Context context) {
        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);
    }

    private void createEvent1(Context context) {
        CreateTicketedEventCommand createEventCommand = new CreateTicketedEventCommand("Lecturer Concert",
                EventType.Theatre,
                200,
                20.00,
                true);

        createEventCommand.execute(context);
    }

    private void createPerformance1Event1(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                1,
                "Edinburgh Castle",
                LocalDateTime.now().minusMonths(1).minusHours(2),
                LocalDateTime.now().minusMonths(1).plusHours(10),
                List.of("John Lonely, Chris Sangwin"),
                true,
                false,
                true,
                100,
                2000);

        addEventPerformanceCommand.execute(context);
    }

    private void createPerformance2Event1(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                1,
                "Edinburgh Castle",
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(8),
                List.of("Nikola Popvic, Clark Barwick"),
                true,
                false,
                false,
                10000,
                200000);

        addEventPerformanceCommand.execute(context);
    }

    private void createEvent2(Context context) {
        CreateTicketedEventCommand createEventCommand = new CreateTicketedEventCommand("MEADOWS PARTY",
                EventType.Music,
                20000,
                20.00,
                true);

        createEventCommand.execute(context);
    }

    private void createPerformance1Event2(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                2,
                "Meadows",
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusDays(10).plusHours(13),
                List.of("Boris Johnson, Donald Trump"),
                false,
                true,
                false,
                1000,
                20000);

        addEventPerformanceCommand.execute(context);
    }

    private void createPerformance2Event2(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                2,
                "Edinburgh College of Art",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(12),
                List.of("Joe Biden, Donald Trump"),
                true,
                false,
                false,
                1000,
                2000);

        addEventPerformanceCommand.execute(context);
    }

    private void cancelEvent2(Context context) {
        CancelEventCommand cancelEvent = new CancelEventCommand(
                2,
                "Performers are fired"
        );

        cancelEvent.execute(context);
    }

    private List<Event> searchEvents1(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                false,
                true,
                LocalDateTime.now().plusMonths(2));

        listEventsOnGivenDate.execute(context);

        return listEventsOnGivenDate.getResult();
    }

    private List<Event> searchEvents2(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                false,
                false,
                LocalDateTime.now().plusMonths(2));

        listEventsOnGivenDate.execute(context);

        return listEventsOnGivenDate.getResult();
    }

    private List<Event> searchEvents3(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                true,
                false,
                LocalDateTime.now().plusMonths(2));

        listEventsOnGivenDate.execute(context);

        return listEventsOnGivenDate.getResult();
    }

    private List<Event> searchEvents4(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                true,
                true,
                LocalDateTime.now().plusMonths(2));

        listEventsOnGivenDate.execute(context);

        return listEventsOnGivenDate.getResult();
    }

    private List<Event> searchEvents5(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                true,
                true,
                LocalDateTime.now().minusMonths(1));

        listEventsOnGivenDate.execute(context);

        return listEventsOnGivenDate.getResult();
    }

    // Entertainment Provider Search for events:
    @Test
    void entProviderSearchBeforeCancelling() {
        loginEntProvider(context);
        List<Event> entEventList1 = searchEvents1(context);
        List<Event> entEventList2 = searchEvents2(context);
        List<Event> entEventList3 = searchEvents3(context);
        List<Event> entEventList4 = searchEvents4(context);
        assertEquals(entEventList1, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
        assertEquals(entEventList1, entEventList2, "All three results should be equal");
        assertEquals(entEventList2, entEventList3, "All three results should be equal");
        assertEquals(entEventList3, entEventList4, "All three results should be equal");
    }

    @Test
    void entProviderSearchAfterCancelling() {
        loginEntProvider(context);
        cancelEvent2(context);

        List<Event> entEventList12 = searchEvents1(context);
        List<Event> entEventList22 = searchEvents2(context);
        assertEquals(entEventList12, List.of(), "After cancelling should be empty list");
        assertEquals(entEventList22, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
    }

    // Consumers Search for events:
    @Test
    void consumer1SearchBeforeCancelling() {
        loginConsumer1(context);
        ConsumerPreferences consumerPreference1 = createConsumerPreferences1();
        User consumer1 = context.getUserState().getCurrentUser();
        ((Consumer) consumer1).setPreferences(consumerPreference1);
        List<Event> conEventList1 = searchEvents1(context);
        List<Event> conEventList2 = searchEvents2(context);
        List<Event> conEventList3 = searchEvents3(context);
        List<Event> conEventList4 = searchEvents4(context);
        assertEquals(conEventList1, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
        assertEquals(conEventList1, conEventList2, "All three results should be equal");
        assertEquals(conEventList2, conEventList3, "All three results should be equal");
        assertEquals(conEventList3, conEventList4, "All three results should be equal");
    }

    @Test
    void consumer2SearchBeforeCancelling() {
        loginConsumer2(context);
        ConsumerPreferences consumerPreference2 = createConsumerPreferences2();
        User consumer2 = context.getUserState().getCurrentUser();
        ((Consumer) consumer2).setPreferences(consumerPreference2);

        List<Event> con2EventList5 = searchEvents5(context);
        assertEquals(con2EventList5, List.of(context.getEventState().findEventByNumber(1)), "Search result should be the same as expected list");
    }

    @Test
    void consumer1SearchAfterCancelling() {
        loginEntProvider(context);
        cancelEvent2(context);
        logOut(context);

        loginConsumer1(context);
        ConsumerPreferences consumerPreference1 = createConsumerPreferences1();
        User consumer1 = context.getUserState().getCurrentUser();
        ((Consumer) consumer1).setPreferences(consumerPreference1);

        List<Event> conEventList12 = searchEvents1(context);
        List<Event> conEventList22 = searchEvents2(context);
        List<Event> conEventList32 = searchEvents3(context);
        List<Event> conEventList42 = searchEvents4(context);
        List<Event> conEventList52 = searchEvents5(context);
        assertEquals(conEventList12, List.of(), "Should be empty after cancelling");
        assertEquals(conEventList22, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
        assertEquals(conEventList22, conEventList32, "The two commands should give same results");
        assertEquals(conEventList42, List.of(), "Should be empty after cancelling");
        assertEquals(conEventList52, List.of(context.getEventState().findEventByNumber(1)), "Search result should be the same as expected list");
    }

    @Test
    void consumer2SearchAfterCancelling() {
        loginEntProvider(context);
        cancelEvent2(context);
        logOut(context);

        loginConsumer2(context);
        ConsumerPreferences consumerPreference2 = createConsumerPreferences2();
        User consumer2 = context.getUserState().getCurrentUser();
        ((Consumer) consumer2).setPreferences(consumerPreference2);

        List<Event> con2EventList12 = searchEvents1(context);
        List<Event> con2EventList22 = searchEvents2(context);
        List<Event> con2EventList32 = searchEvents3(context);
        List<Event> con2EventList42 = searchEvents4(context);
        List<Event> con2EventList52 = searchEvents5(context);
        assertEquals(con2EventList12, List.of(), "Should be empty after cancelling");
        assertEquals(con2EventList22, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
        assertEquals(con2EventList32, List.of(), "Should be empty after cancelling");
        assertEquals(con2EventList42, List.of(), "Should be empty after cancelling");
        assertEquals(con2EventList52, List.of(context.getEventState().findEventByNumber(1)), "Search result should be the same as expected list");
    }

    @Test
    void consumer3SearchAfterCancelling() {
        loginEntProvider(context);
        cancelEvent2(context);
        logOut(context);

        loginConsumer3(context);
        ConsumerPreferences consumerPreference3 = createConsumerPreferences3();
        User consumer3 = context.getUserState().getCurrentUser();
        ((Consumer) consumer3).setPreferences(consumerPreference3);

        List<Event> con3EventList12 = searchEvents1(context);
        List<Event> con3EventList22 = searchEvents2(context);
        List<Event> con3EventList32 = searchEvents3(context);
        List<Event> con3EventList42 = searchEvents4(context);
        List<Event> con3EventList52 = searchEvents5(context);
        assertEquals(con3EventList12, List.of(), "Should be empty after cancelling");
        assertEquals(con3EventList22, List.of(context.getEventState().findEventByNumber(2)), "Search result should be the same as expected list");
        assertEquals(con3EventList32, List.of(), "Should be empty after cancelling");
        assertEquals(con3EventList42, List.of(), "Should be empty after cancelling");
        assertEquals(con3EventList52, List.of(), "Should be empty after cancelling");
    }
}