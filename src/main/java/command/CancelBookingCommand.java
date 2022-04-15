package command;

import controller.Context;
import external.PaymentSystem;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;

public class CancelBookingCommand implements ICommand {
    enum LogStatus {
        CANCEL_BOOKING_SUCCESS,
        CANCEL_BOOKING_USER_NOT_CONSUMER,
        CANCEL_BOOKING_BOOKING_NOT_FOUND,
        CANCEL_BOOKING_USER_IS_NOT_BOOKER,
        CANCEL_BOOKING_BOOKING_NOT_ACTIVE,
        CANCEL_BOOKING_REFUND_FAILED,
        CANCEL_BOOKING_NO_CANCELLATIONS_WITHIN_24H
    }
    private long bookingNumber;
    private boolean successResult;

    public CancelBookingCommand(long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        // Condition checks - abort execution if condition is satisfied
        Booking booking = context.getBookingState().findBookingByNumber(bookingNumber);

        if (booking == null) {
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_BOOKING_NOT_FOUND);
            return;
        }

        Event event = context.getEventState().findEventByNumber(bookingNumber);
        User user = context.getUserState().getCurrentUser();
        EventPerformance eventPerformance = booking.getEventPerformance();
        LocalDateTime performanceStart = eventPerformance.getStartDateTime();
        PaymentSystem paymentSystem = context.getPaymentSystem();

        if (!(user instanceof Consumer)) {
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_USER_NOT_CONSUMER);
            return;
        }

        if (booking.getStatus() != BookingStatus.Active) {
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_BOOKING_NOT_ACTIVE);
            return;
        }

        if (!booking.getBooker().equals(user)) {
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_USER_IS_NOT_BOOKER);
            return;
        }

        if (performanceStart.minusHours(24).isBefore(LocalDateTime.now())) {
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_NO_CANCELLATIONS_WITHIN_24H);
            return;
        }

        // Check if it's successfully refunded.
        successResult = paymentSystem.processRefund(user.getPaymentAccountEmail(), event.getOrganiser().getPaymentAccountEmail(), booking.getAmountPaid());

        // After all the conditions are met, make sure the booking is logged as cancelled
        if(successResult) {
            booking.cancelByConsumer();
            event.getOrganiser().getProviderSystem().cancelBooking(bookingNumber);
            logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_SUCCESS);
        }
        else logger.logAction("CancelBookingCommand", LogStatus.CANCEL_BOOKING_REFUND_FAILED);
    }

    @Override
    public Boolean getResult() {
        return successResult;
    }

}