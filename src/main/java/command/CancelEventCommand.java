package command;

import external.PaymentSystem;
import logging.Logger;
import model.*;
import java.time.LocalDateTime;
import java.util.List;

import controller.Context;

public class CancelEventCommand implements ICommand {
    private long eventNumber;
    private String organiserMessage;
    private boolean result;

    public CancelEventCommand(long eventNumber, String organiserMessage) {
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        User currentUser = context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(eventNumber);
        PaymentSystem paymentSystem = context.getPaymentSystem();

        if (!(currentUser instanceof EntertainmentProvider) ||
                (event == null) ||
                (event.getStatus() != EventStatus.ACTIVE) ||
                (organiserMessage == null) || (organiserMessage.equals("")) ||
                (currentUser != event.getOrganiser())) {
            return;
        }
        for (EventPerformance ep : event.getPerformances()) {
            if ((LocalDateTime.now().isAfter(ep.getStartDateTime())) ||
                    (LocalDateTime.now().isAfter(ep.getEndDateTime()))) {
                return;
            }
        }

        // Getting information to see if the refund happens properly
        if (event instanceof TicketedEvent) {
            TicketedEvent ticketedEvent = (TicketedEvent) event;
            // Calculate the amount of sponsorship to be refunded to the government
            double discountedTicketPrice = ticketedEvent.getDiscountedTicketPrice();
            double originalPrice = ticketedEvent.getOriginalTicketPrice();
            int numTickets = ticketedEvent.getNumTickets();
            double sponsorshipAmount = (originalPrice - discountedTicketPrice) * numTickets;
            // Gather the emails of the event provider and government for refunding
            String governmentEmail = ticketedEvent.getSponsorAccountEmail();
            String entertainmentProviderEmail = event.getOrganiser().getEmail();
            paymentSystem.processRefund(governmentEmail, entertainmentProviderEmail, sponsorshipAmount);
        }

            // If the conditions are passed, then cancel all the bookings of this event, and refund the payment

        List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
        for (Booking booking : bookings) {
            // First set the booking state to be cancelled
            booking.cancelByProvider();

            // notify consumer of cancellation
            Consumer buyer = booking.getBooker();
            buyer.notify(organiserMessage);

            // Refund the booking if ticketed
            if (event instanceof TicketedEvent) {
                double discountedTicketPrice = ((TicketedEvent) event).getDiscountedTicketPrice();
                paymentSystem.processRefund(buyer.getEmail(), event.getOrganiser().getEmail(), discountedTicketPrice);
            }
        }
        result = true;
        event.cancel();
        for (Booking booking: context.getBookingState().findBookingsByEventNumber(eventNumber)){
            // Change the booking status correspondingly
            booking.cancelByProvider();
        }

        Logger.getInstance().logAction("CancelEventCommand", result);
    }

    @Override
    public Boolean getResult() {
        return result;
    }

}