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

public class SearchForEventsTest {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
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

    private void createConsumer1(Context context) {
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


    private void loginConsume1(Context context) {
        LoginCommand login = new LoginCommand("Consumer1sEmail@somewhere.com", "ConsumerPassword");
        login.execute(context);
    }

    private void loginConsume2(Context context) {
        LoginCommand login = new LoginCommand("Consumer2sEmail@somewhere.com", "InterestingPassword");
        login.execute(context);
    }

    private void loginConsume3(Context context) {
        LoginCommand login = new LoginCommand("Consumer3sEmail@somewhere.com", "password");
        login.execute(context);
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
                LocalDateTime.now().minusMonths(1).minusHours(2),
                LocalDateTime.now().plusMonths(1).minusHours(2),
                List.of("John Lonely, Chris Sangwin"),
                true,
                false,
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
                false,
                true,
                false,
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

    private EventPerformance createPerformance1Event2(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                2,
                "Meadows",
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusDays(10).plusHours(13),
                List.of("Boris Johnson, Donald Trump"),
                true,
                false,
                false,
                10000,
                200000);

        addEventPerformanceCommand.execute(context);

        return addEventPerformanceCommand.getResult();
    }

    private EventPerformance createPerformance2Event2(Context context) {
        AddEventPerformanceCommand addEventPerformanceCommand = new AddEventPerformanceCommand(
                2,
                "Edinburgh College of Art",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(4).plusHours(12),
                List.of("Joe Biden, Donald Trump"),
                true,
                false,
                false,
                1000,
                2000);

        addEventPerformanceCommand.execute(context);

        return addEventPerformanceCommand.getResult();
    }

    private void cancelEvent1(Context context) {
        CancelEventCommand cancelEvent = new CancelEventCommand(
                0,
                "Performers are fired"
        );
    }

    private void searchEvents1(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                false,
                true,
                LocalDateTime.now().plusMonths(3));
    }

    private void searchEvents2(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                false,
                false,
                LocalDateTime.now().plusMonths(3));
    }

    private void searchEvents3(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                true,
                false,
                LocalDateTime.now().plusMonths(3));
    }

    private void searchEvents4(Context context) {
        ListEventsOnGivenDateCommand listEventsOnGivenDate = new ListEventsOnGivenDateCommand(
                true,
                true,
                LocalDateTime.now().plusMonths(3));
    }

    @Test
    void searchForEventsTest() {

    }
}
