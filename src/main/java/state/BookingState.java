package state;

import model.Booking;
import model.EventPerformance;

import java.util.function.Consumer;

public class BookingState implements IBookingState {

    public BookingState() {

    };

    public BookingState(IBookingState other) {

    };

    @Override
    public Booking findBookingByNumber(long bookingNumber) {

    };

    @Override
    public Booking findBookingsByEventNumber(long eventNumber) {

    }

    @Override
    public Booking createBooking(model.Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {
        return null;
    }

    ;

    @Override
    public Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {

    };

}