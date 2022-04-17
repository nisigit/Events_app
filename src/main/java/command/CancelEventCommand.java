package command;

import external.PaymentSystem;
import logging.Logger;
import model.*;
import java.time.LocalDateTime;
import java.util.List;

import controller.Context;

public class CancelEventCommand implements ICommand {

    enum LogStatus {
        CANCEL_EVENT_SUCCESS,
        CANCEL_EVENT_MESSAGE_MUST_NOT_BE_BLANK,
        CANCEL_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER,
        CANCEL_EVENT_EVENT_NOT_FOUND,
        CANCEL_EVENT_NOT_ACTIVE,
        CANCEL_EVENT_USER_NOT_ORGANISER,
        CANCEL_EVENT_PERFORMANCE_ALREADY_STARTED,
        CANCEL_EVENT_REFUND_SPONSORSHIP_SUCCESS,
        CANCEL_EVENT_REFUND_SPONSORSHIP_FAILED,
        CANCEL_EVENT_REFUND_BOOKING_SUCCESS,
        CANCEL_EVENT_REFUND_BOOKING_ERROR,
    }

    private long eventNumber;
    private String organiserMessage;
    private boolean result;

    public CancelEventCommand(long eventNumber, String organiserMessage) {
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }

    private boolean userIsAllowedToCancelEvent(Context context, Event event) {
        User currentUser = context.getUserState().getCurrentUser();
        if (!(currentUser instanceof EntertainmentProvider)) {
            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER);
            return false;
        }

        if (currentUser != event.getOrganiser()) {
            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_USER_NOT_ORGANISER);
            return false;
        }

        if ((event.getStatus() != EventStatus.ACTIVE)) {
            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_NOT_ACTIVE);
            return false;
        }

        return true;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        Event event = context.getEventState().findEventByNumber(eventNumber);
        PaymentSystem paymentSystem = context.getPaymentSystem();
        boolean refundSponsorshipResult = true;
        boolean refundBookingResult = false;

        if (event == null) {
            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_EVENT_NOT_FOUND);
            return;
        }
        if ((organiserMessage == null) || (organiserMessage.equals(""))) {
            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_MESSAGE_MUST_NOT_BE_BLANK);
            return;
        }

        if (!(userIsAllowedToCancelEvent(context, event))) {
            return;
        }

        for (EventPerformance ep : event.getPerformances()) {

            if ((LocalDateTime.now().isAfter(ep.getStartDateTime())) ||
                    (LocalDateTime.now().isAfter(ep.getEndDateTime()))) {
                Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_PERFORMANCE_ALREADY_STARTED);
                return;
            }
        }

        // Getting information to see if the refund happens properly
        if (event instanceof TicketedEvent) {
            TicketedEvent ticketedEvent = (TicketedEvent) event;
            if (ticketedEvent.isSponsored()) {
                // Calculate the amount of sponsorship to be refunded to the government
                double discountedTicketPrice = ticketedEvent.getDiscountedTicketPrice();
                double originalPrice = ticketedEvent.getOriginalTicketPrice();
                int numTickets = ticketedEvent.getNumTickets();
                double sponsorshipAmount = (originalPrice - discountedTicketPrice) * numTickets;
                // Gather the emails of the event provider and government for refunding
                String governmentEmail = ticketedEvent.getSponsorAccountEmail();
                String entertainmentProviderEmail = event.getOrganiser().getEmail();
                refundSponsorshipResult = paymentSystem.processRefund(governmentEmail, entertainmentProviderEmail, sponsorshipAmount);
                if (refundSponsorshipResult) {
                    Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_REFUND_SPONSORSHIP_SUCCESS);
                }
                else {
                    Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_REFUND_SPONSORSHIP_FAILED);
                }
            }
        }

        // If the conditions are passed, then cancel all the bookings of this event, and refund the payment

        List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
        if (bookings.size() == 0) {refundBookingResult = true;}
        else {
            for (Booking booking : bookings) {
                // First set the booking state to be cancelled
                booking.cancelByProvider();

                // notify consumer of cancellation
                Consumer buyer = booking.getBooker();
                buyer.notify(organiserMessage);

                // Refund the booking if ticketed
                if (event instanceof TicketedEvent) {
                    refundBookingResult = paymentSystem.processRefund(buyer.getEmail(), event.getOrganiser().getPaymentAccountEmail(), booking.getAmountPaid());
                    if (refundBookingResult) {
                        Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_REFUND_BOOKING_SUCCESS);
                    }
                    else {
                        Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_REFUND_BOOKING_ERROR);
                    }
                }
            }
        }

        if (((event instanceof TicketedEvent) && refundSponsorshipResult && refundBookingResult) || (event instanceof NonTicketedEvent)) {
            result = true;
            event.cancel();
            for (Booking booking: context.getBookingState().findBookingsByEventNumber(eventNumber)){
                // Change the booking status correspondingly
                booking.cancelByProvider();
            }

            Logger.getInstance().logAction("CancelEventCommand", LogStatus.CANCEL_EVENT_SUCCESS);
        }
    }

    @Override
    public Boolean getResult() {
        return result;
    }

}