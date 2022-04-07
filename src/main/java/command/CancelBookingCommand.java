package command;

import controller.Context;
import external.PaymentSystem;
import logging.Logger;
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
        // Condition checks - abort execution if condition is satisfied
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
        // Check if it's successfully refunded.
        result = paymentSystem.processRefund(user.getPaymentAccountEmail(), event.getOrganiser().getPaymentAccountEmail(), booking.getAmountPaid());

        // After all the conditions are met, make sure the booking is logged as cancelled
        if(result) booking.cancelByConsumer();

        Logger.getInstance().logAction("CancelBookingCommand", result);
    }

    @Override
    public Boolean getResult() {
        return result;
    }

}