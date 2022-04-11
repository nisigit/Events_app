package model;

import java.time.LocalDateTime;

public class Booking {
    private long bookingNumber;
    private Consumer booker;
    private EventPerformance performance;
    private int numTickets;
    private double amountPaid;
    private LocalDateTime bookingDateTime;
    private BookingStatus status;

    public Booking(long bookingNumber, Consumer booker, EventPerformance performance, int numTickets,
                   double amountPaid, LocalDateTime bookingDateTime) {
        this.bookingNumber = bookingNumber;
        this.booker = booker;
        this.performance = performance;
        this.numTickets = numTickets;
        this.amountPaid = amountPaid;
        this.bookingDateTime = bookingDateTime;
        this.status = BookingStatus.Active;
    }

    public long getBookingNumber() {
        return this.bookingNumber;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public Consumer getBooker() {
        return this.booker;
    }

    public EventPerformance getEventPerformance() {
        return this.performance;
    }

    public double getAmountPaid() {
        return this.amountPaid;
    }

    public void cancelByConsumer() {
        status = BookingStatus.CancelledByConsumer;
    }

    public void cancelPaymentFailed() {
        status = BookingStatus.PaymentFailed;
    }

    public void cancelByProvider() {
        status = BookingStatus.CancelledByProvider;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingNumber=" + bookingNumber +
                ", booker=" + booker +
                ", performance=" + performance +
                ", numTickets=" + numTickets +
                ", amountPaid=" + amountPaid +
                ", bookingDateTime=" + bookingDateTime +
                ", status=" + status +
                '}';
    }

}