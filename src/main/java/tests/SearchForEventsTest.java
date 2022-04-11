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

    private Consumer createConsumer1(Context context) {
        Consumer consumer =
                new Consumer(
                        "Consumer",
                        "Consumer1sEmail@somewhere.com",
                        "randomPhoneNumber",
                        "ConsumerPassword",
                        "ConsumerPaymentEmail@somewhere.com");
        context.getUserState().addUser(consumer);
        return consumer;
    }

    private Consumer createConsumer2(Context context) {
        Consumer consumer =
                new Consumer(
                        "Consumer2",
                        "Consumer2sEmail@somewhere.com",
                        "HateRandomPhoneNumber",
                        "InterestingPassword",
                        "Idon'tpay@somewhere.com");
        context.getUserState().addUser(consumer);
        return consumer;
    }

    private Consumer createConsumer3(Context context) {
        Consumer consumer =
                new Consumer(
                        "Consumer3",
                        "Consumer3sEmail@somewhere.com",
                        "911",
                        "password",
                        "Rich@somewhere.com");
        context.getUserState().addUser(consumer);
        return consumer;
    }

    private ConsumerPreferences createConsumerPreferences1() {
        return new ConsumerPreferences(true, false, false, 100, 2000);
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

    @Test
    void searchForEventsTest() {

    }
}
