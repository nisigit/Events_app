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
    private Context context;
    private CancelBookingCommand cancelBookingCommand1, cancelBookingCommand2, cancelBookingCommand3, cancelBookingCommand4;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @BeforeEach
    void setup() {
        // Set up some required actions and objects
        this.context = new Context();
        createEntProvider(context);
        createConsumer(context);
        createConsumer(context);
        loginEntProvider(context);
        createEvent1(context);
        createEvent2(context);
        createPerformance1Event1(context);
        createPerformance2Event1(context);
        createPerformanceEvent2(context);
        logOut(context);
        loginConsumer(context);
        long bookingNumber1 = bookEvent1Performance1(context);
        long bookingNumber2 = bookEvent1Performance2(context);
        long bookingNumber3 = bookEvent2Performance1(context);
        this.cancelBookingCommand1 = new CancelBookingCommand(bookingNumber1);
        this.cancelBookingCommand2 = new CancelBookingCommand(bookingNumber2);
        this.cancelBookingCommand3 = new CancelBookingCommand(bookingNumber3);
        this.cancelBookingCommand4 = new CancelBookingCommand(Integer.toUnsignedLong(10));
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

    private void createConsumer(Context context) {
        Consumer consumer =
                new Consumer(
                        "Consumer",
                        "ConsumersEmail@somewhere.com",
                        "randomPhoneNumber",
                        "ConsumerPassword",
                        "ConsumerPaymentEmail@somewhere.com");
        context.getUserState().addUser(consumer);
    }

    private void loginConsumer(Context context) {
        LoginCommand login = new LoginCommand("ConsumersEmail@somewhere.com", "ConsumerPassword");
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
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(2),
                List.of("John Lonely, Chris Sangwin"),
                true,
                true,
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
                true,
                true,
                1000,
                20000);

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

    private void createPerformanceEvent2(Context context) {
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
    void successfullyCancelled(){
        cancelBookingCommand1.execute(context);
        assertTrue(cancelBookingCommand1.getResult());
        assertEquals(context.getBookingState().findBookingByNumber(Integer.toUnsignedLong(1)).getStatus(), BookingStatus.CancelledByConsumer);
    }

    @Test
    void wrongBookingNumber() {
        cancelBookingCommand4.execute(context);
        assertNull(context.getBookingState().findBookingByNumber(Integer.toUnsignedLong(10)));
        assertFalse(cancelBookingCommand4.getResult());
    }

    @Test
    void within24Hours() {
        cancelBookingCommand3.execute(context);
        cancelBookingCommand2.execute(context);
        assertFalse(cancelBookingCommand3.getResult());
        assertFalse(cancelBookingCommand2.getResult());
        assertEquals(context.getBookingState().findBookingByNumber(Integer.toUnsignedLong(3)).getStatus(), BookingStatus.Active);
        assertEquals(context.getBookingState().findBookingByNumber(Integer.toUnsignedLong(2)).getStatus(), BookingStatus.Active);
    }

}
