package command;
import external.PaymentSystem;
import model.*;
import java.time.LocalDateTime;


import controller.Context;

import java.time.LocalDateTime;

public class CancelEventCommand implements ICommand {
    private long eventNumber;
    private String organiserMessage;
    private boolean result;

    public CancelEventCommand(long eventNumber, String organiserMessage) {
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    };

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(eventNumber);
        EventStatus status = context.getEventState().findEventByNumber(eventNumber).getStatus();
        EntertainmentProvider organiser = event.getOrganiser();
        PaymentSystem paymentSystem = context.getPaymentSystem();

        if (!(currentUser instanceof EntertainmentProvider) &&
                (event != null) &&
                (status == EventStatus.ACTIVE) &&
                (organiserMessage != null) &&
                (currentUser == organiser)) {
            result = true;
        }
        for (EventPerformance ep: event.getPerformances()) {
            if ((LocalDateTime.now().isAfter(ep.getStartDateTime())) ||
                    (LocalDateTime.now().isAfter(ep.getEndDateTime()))) {
                result = false;
            }
        }
        if (event instanceof TicketedEvent) {
            TicketedEvent ticketedEvent = (TicketedEvent) event;
            double discountedTicketPrice = ticketedEvent.getDiscountedTicketPrice();
            double originalPrice = ticketedEvent.getOriginalTicketPrice();
            int numTickets = ticketedEvent.getNumTickets();
            double sponsorshipAmount = (originalPrice - discountedTicketPrice) * numTickets;
            String governmentEmail = ticketedEvent.getSponsorAccountEmail();
            String entertainmentProviderEmail = organiser.getEmail();
            if (paymentSystem.processRefund(governmentEmail, entertainmentProviderEmail, sponsorshipAmount)) {
                result = true;
            }
        }

    };

    @Override
    public Boolean getResult() {
        return result;
    };

}