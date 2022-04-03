package command;

import controller.Context;
import external.PaymentSystem;
import model.*;

import java.time.LocalDateTime;

public class CancelBookingCommand implements ICommand {

    private long bookingNumber;
    private boolean result;

    public CancelBookingCommand(long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    @Override
    public void execute(Context context) {
        // Condition check
        Booking booking = context.getBookingState().findBookingByNumber(bookingNumber);
        Event event = context.getEventState().findEventByNumber(bookingNumber);
        User user = context.getUserState().getCurrentUser();
        EventPerformance eventPerformance = booking.getEventPerformance();
        LocalDateTime performanceStart = eventPerformance.getStartDateTime();
        PaymentSystem paymentSystem = context.getPaymentSystem();

        if (booking == null) {
            return;
        }
        if (!(user instanceof Consumer) ||
                booking.getStatus() != BookingStatus.Active ||
                booking.getBooker() != user ||
                performanceStart.minusHours(24).isBefore(LocalDateTime.now())) {
            return;
        }
        if(user instanceof Consumer) booking.cancelByConsumer();

        // Check if it's successfully refunded to see if it's successfully cancelled
        result = paymentSystem.processRefund(user.getPaymentAccountEmail(), event.getOrganiser().getPaymentAccountEmail(), booking.getAmountPaid());
    }

    @Override
    public Boolean getResult() {
        return result;
    }

}