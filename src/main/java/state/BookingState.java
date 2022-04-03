package state;

import model.Booking;
import model.EventPerformance;
import model.Consumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingState implements IBookingState {

    private long nextBookingNumber = 0;
    private Map<Long, List<Booking>> bookings;

    public BookingState() {
        this.nextBookingNumber++;
        this.bookings = new HashMap<>();
    }

    public BookingState(IBookingState other) {
        BookingState x = (BookingState) other;
        this.nextBookingNumber = x.getNextBookingNumber();
        this.bookings = x.getBookings();
    }

    @Override
    public Booking findBookingByNumber(long bookingNumber) {
        for (Long eventNum : bookings.keySet()) {
            for (Booking booking : bookings.get(eventNum)) {
                if (booking.getBookingNumber() == bookingNumber) {
                    return booking;
                }
            }
        }
        return null;
    }

    @Override
    public List<Booking> findBookingsByEventNumber(long eventNumber) {
        return bookings.get(eventNumber);
    }

    public Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {
        Booking newBooking = new Booking(this.nextBookingNumber, booker, performance,
                                        numTickets, amountPaid, LocalDateTime.now());
        this.nextBookingNumber++;
        if (!bookings.containsKey(performance.getEvent().getEventNumber())) {
            bookings.put(performance.getEvent().getEventNumber(), new ArrayList<>());
        }
        this.bookings.get(performance.getEvent().getEventNumber()).add(newBooking);
        return newBooking;
    }

    public long getNextBookingNumber() {
        return nextBookingNumber;
    }

    public Map<Long, List<Booking>> getBookings() {
        return bookings;
    }
}