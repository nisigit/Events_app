package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import external.PaymentSystem;
import model.*;
import state.IBookingState;

import java.time.LocalDateTime;

public class BookEventCommand implements ICommand {

    private long eventNumber, performanceNumber;
    private int numTicketsRequested;
    private boolean paymentSuccess;
    private Long newBookingNumber;

    public BookEventCommand(long eventNumber, long performanceNumber, int numTicketsRequested) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    @Override
    public void execute(Context context) {
        Event event = context.getEventState().findEventByNumber(eventNumber);
        User user = context.getUserState().getCurrentUser();
        PaymentSystem paymentSystem = context.getPaymentSystem();
        paymentSuccess = false;
        newBookingNumber = null;

        if (!(event != null && (user instanceof Consumer) && (event instanceof TicketedEvent))) {
            return;
        }

        EventPerformance eventPerformance = event.getPerformanceByNumber(performanceNumber);

        if (!(numTicketsRequested >= 1 &&
                eventPerformance != null)) {
            return;
        }

        TicketedEvent ticketedEvent = (TicketedEvent) event;
        EntertainmentProviderSystem system = ticketedEvent.getOrganiser().getProviderSystem();
        if (!(eventPerformance.getEndDateTime().isAfter(LocalDateTime.now()) &&
                system.getNumTicketsLeft(eventNumber, performanceNumber) >= numTicketsRequested)) {
            return;
        }
        double transactionAmount = ticketedEvent.getDiscountedTicketPrice() * numTicketsRequested;
        paymentSuccess = paymentSystem.processPayment(user.getPaymentAccountEmail(), event.getOrganiser().getPaymentAccountEmail(), transactionAmount);

        IBookingState bookingState = context.getBookingState();
        Booking newBooking = bookingState.createBooking((Consumer) user, eventPerformance, (int) numTicketsRequested, transactionAmount);
        newBookingNumber = newBooking.getBookingNumber();

        if (!paymentSuccess) {
            newBooking.cancelPaymentFailed();
        }

        system.recordNewBooking(eventNumber, performanceNumber, newBookingNumber, ((Consumer) user).getName(), user.getEmail(), numTicketsRequested);
    }

    @Override
    public Long getResult() {
        return newBookingNumber;
    }

}