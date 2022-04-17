package tests;
import logging.*;
import model.*;
import state.BookingState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import static org.testng.Assert.*;

import java.time.LocalDateTime;
import java.util.*;

public class TestBookingState {
    EntertainmentProvider entertainmentProvider;
    Consumer consumer1;
    TicketedEvent ticketedEvent, ticketedEvent2;
    EventPerformance eventPerformance1, eventPerformance2;
    BookingState bookingState0;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @BeforeEach
    void setUp() {
        this.entertainmentProvider = new EntertainmentProvider("Uni of Edinburgh", "Old College", "entProviderPaymentEmail@ed.ac.uk", "King Stuart", "KingStuart@ed.ac.uk", "password", List.of("Clark Barwick"), List.of("otheremails@ed.ac.uk"));
        this.consumer1 = new Consumer("Hugo He", "HugoBoss@gmail.com", "13898812138", "HUGOBOSS", "HugoBoss@gmail.com");
        this.ticketedEvent = new TicketedEvent(1, entertainmentProvider, "Fei Cheng Wu Rao", EventType.Theatre, 8.88, 200);
        this.eventPerformance1 = new EventPerformance(1, ticketedEvent, "Gordon Aikman Lecture Theatre", LocalDateTime.now(), LocalDateTime.now().plusHours(3), List.of("Nv Jia Bin"), false, false, false, 200, 500);
        this.ticketedEvent2 = new TicketedEvent(2, entertainmentProvider, "NCAA", EventType.Sports, 20, 2000);
        this.eventPerformance2 = new EventPerformance(2, ticketedEvent2, "Uni Gym", LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(1).plusHours(3), List.of("Hugo He", "Massimo Wu", "Victor Wang"), true, true, true, 1000, 2000);
        this.bookingState0 = new BookingState();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }


    @Test
    void TestConstructors() {
        assertEquals(bookingState0.getBookings(), new HashMap<>(), "Should be initialized as empty hash map");
        assertEquals(bookingState0.getNextBookingNumber(), 1, "Should be initialized as 1");

        BookingState bookingState1 = new BookingState(bookingState0);
        assertEquals(bookingState1, bookingState0, "Should have the same values");
        assertNotSame(bookingState1, bookingState0, "Should be a deep copy");
    }

    @Test
    void TestCreateBooking() {
        Booking booking1 = bookingState0.createBooking(consumer1, eventPerformance1, 2, 17.76);
        LocalDateTime time1 = booking1.getBookingDateTime();
        Booking booking2 = bookingState0.createBooking(consumer1, eventPerformance1, 4, 35.52);
        LocalDateTime time2 = booking2.getBookingDateTime();
        Booking booking3 = bookingState0.createBooking(consumer1, eventPerformance2, 1, 20);
        LocalDateTime time3 = booking3.getBookingDateTime();
        Booking booking12 = new Booking(1, consumer1, eventPerformance1, 2, 17.76, time1);
        Booking booking22 = new Booking(2, consumer1, eventPerformance1, 4, 35.52, time2);
        Booking booking32 = new Booking(3, consumer1, eventPerformance2, 1, 20, time3);
        assertEquals(booking1, booking12, "Should be equal with same fields");
        assertEquals(booking2, booking22, "Should be equal with same fields");
        assertEquals(booking3, booking32, "Should be equal with same fields");
    }

    @Test
    void TestFindBookingByNumber() {
        Booking booking1 = bookingState0.createBooking(consumer1, eventPerformance1, 2, 17.76);
        Booking booking2 = bookingState0.createBooking(consumer1, eventPerformance1, 4, 35.52);
        Booking booking3 = bookingState0.createBooking(consumer1, eventPerformance2, 1, 20);
        assertSame(bookingState0.findBookingByNumber(1), booking1, "Should be the same object");
        assertSame(bookingState0.findBookingByNumber(2), booking2, "Should be the same object");
        assertSame(bookingState0.findBookingByNumber(3), booking3, "Should be the same object");
        assertNull(bookingState0.findBookingByNumber(4), "Should be null with non-existed booking number");
    }

    @Test
    void TestFindBookingsByEventNumber() {
        Booking booking1 = bookingState0.createBooking(consumer1, eventPerformance1, 2, 17.76);
        Booking booking2 = bookingState0.createBooking(consumer1, eventPerformance1, 4, 35.52);
        Booking booking3 = bookingState0.createBooking(consumer1, eventPerformance2, 1, 20);
        assertEquals(bookingState0.findBookingsByEventNumber(1), List.of(booking1, booking2), "Should give a list of first 2 bookings");
        assertEquals(bookingState0.findBookingsByEventNumber(2), List.of(booking3), "Should give a list of the last booking");
        assertNull(bookingState0.findBookingByNumber(4), "Should be null with non-existed booking number");
    }

    @Test
    void TestGetNextBookingNumber() {
        assertEquals(bookingState0.getNextBookingNumber(), 1, "Initialized as 1");
        Booking booking1 = bookingState0.createBooking(consumer1, eventPerformance1, 2, 17.76);
        assertEquals(bookingState0.getNextBookingNumber(), 2, "Should be 2 after 1 booking is created");
        Booking booking2 = bookingState0.createBooking(consumer1, eventPerformance1, 4, 35.52);
        assertEquals(bookingState0.getNextBookingNumber(), 3, "Should be 3 after 2 bookings are created");
        Booking booking3 = bookingState0.createBooking(consumer1, eventPerformance2, 1, 20);
        assertEquals(bookingState0.getNextBookingNumber(), 4, "Should be 4 after 3 bookings are created");
    }

    @Test
    void TestGetBookings() {
        Booking booking1 = bookingState0.createBooking(consumer1, eventPerformance1, 2, 17.76);
        Booking booking2 = bookingState0.createBooking(consumer1, eventPerformance1, 4, 35.52);
        Booking booking3 = bookingState0.createBooking(consumer1, eventPerformance2, 1, 20);
        HashMap<Long, List<Booking>> expectedBookings = new HashMap<>();
        expectedBookings.put(Integer.toUnsignedLong(1), List.of(booking1, booking2));
        expectedBookings.put(Integer.toUnsignedLong(2), List.of(booking3));
        assertEquals(bookingState0.getBookings(), expectedBookings, "Should give the same result as the expected Hash map");
    }

}
