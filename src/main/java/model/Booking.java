package model;

public class Booking extends Object {

    public Booking(long bookingNumber, Consumer booker, EventPerformance performance, int numTickets, double amountPaid, LocalDateTime bookingDateTime) {

    };

    public long getBookingNumber() {

    };

    public BookingStatus getStatus() {

    };

    public Consumer getBooker() {

    };

    public EventPerformance getEventPerformance() {

    };

    public double getAmountPaid() {

    };

    public void cancelByConsumer() {

    };

    public void cancelPaymentFailed() {

    };

    public void cancelByProvider() {

    };

    public String toString() {

    };

}