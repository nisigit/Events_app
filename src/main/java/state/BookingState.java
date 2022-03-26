package state;

import model.Booking;
import model.EventPerformance;
import model.Consumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingState implements IBookingState {

    private long nextBookingNumber = 0;
    private ArrayList<Booking> bookings;

    public BookingState() {
        this.nextBookingNumber++;
        this.bookings = new ArrayList<>();
    }

    public BookingState(IBookingState other) {
        BookingState x = (BookingState) other;
        this.nextBookingNumber = x.getNextBookingNumber();
        this.bookings = x.getBookings();
    }

    @Override
    public Booking findBookingByNumber(long bookingNumber) {
        for (Booking i: this.bookings) {
            if (i.getBookingNumber() == bookingNumber) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Booking> findBookingsByEventNumber(long eventNumber) {
        ArrayList<Booking> temp = new ArrayList<>();
        for (Booking i : this.bookings) {
            if (i.getEventPerformance().getEvent().getEventNumber() == eventNumber) {
                temp.add(i);
            }
        }
        return temp;
    }

    public Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {
        Booking newBooking = new Booking(this.nextBookingNumber, booker, performance,
                                        numTickets, amountPaid, LocalDateTime.now());
        this.nextBookingNumber++;
        return newBooking;
    }

    public long getNextBookingNumber() {
        return nextBookingNumber;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}