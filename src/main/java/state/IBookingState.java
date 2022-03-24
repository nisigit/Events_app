package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;

public interface IBookingState {

    Booking findBookingByNumber(long bookingNumber);

    Booking findBookingsByEventNumber(long eventNumber);

    Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid);

}