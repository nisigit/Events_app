package model;

import java.time.LocalDateTime;
import java.util.Objects;

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

    // For the Test Sake
    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingNumber=" + bookingNumber +
                ", booker=" + booker.getName() +
                ", performance=" + performance +
                ", numTickets=" + numTickets +
                ", amountPaid=" + amountPaid +
                ", bookingDateTime=" + bookingDateTime +
                ", status=" + status +
                '}';
    }

    // For Test Sake
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingNumber == booking.bookingNumber && numTickets == booking.numTickets && Double.compare(booking.amountPaid, amountPaid) == 0 && Objects.equals(booker, booking.booker) && Objects.equals(performance, booking.performance) && Objects.equals(bookingDateTime, booking.bookingDateTime) && status == booking.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingNumber, booker, performance, numTickets, amountPaid, bookingDateTime, status);
    }
}