package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import external.PaymentSystem;
import logging.Logger;
import model.*;
import state.IBookingState;

import java.time.LocalDateTime;

public class BookEventCommand implements ICommand {

    enum LogStatus {
        BOOK_EVENT_SUCCESS,
        BOOK_EVENT_USER_NOT_CONSUMER,
        BOOK_EVENT_NOT_A_TICKETED_EVENT,
        BOOK_EVENT_EVENT_NOT_ACTIVE,
        BOOK_EVENT_ALREADY_OVER,
        BOOK_EVENT_INVALID_NUM_TICKETS,
        BOOK_EVENT_NOT_ENOUGH_TICKETS_LEFT,
        BOOK_EVENT_PAYMENT_FAILED,
        BOOK_EVENT_EVENT_NOT_FOUND,
        BOOK_EVENT_PERFORMANCE_NOT_FOUND,
    }

    private long eventNumber, performanceNumber;
    private int numTicketsRequested;
    private boolean paymentSuccess;
    private Long bookingNumberResult;

    public BookEventCommand(long eventNumber, long performanceNumber, int numTicketsRequested) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    @Override
    public void execute(Context context) {
        // Condition checks - abort execution if a condition is satisfied
        Event event = context.getEventState().findEventByNumber(eventNumber);
        User user = context.getUserState().getCurrentUser();
        PaymentSystem paymentSystem = context.getPaymentSystem();
        paymentSuccess = false;
        bookingNumberResult = null;

        // Using assertions to check conditions

//        assert (!(user instanceof Consumer)): "User is not a consumer";
//        assert (event == null): "Event can't be null";
//        assert (!(event instanceof TicketedEvent)): "The event is not tickted";
//        assert (!(numTicketsRequested >= 1)): "Minimum booking 1 ticket";
//        EventPerformance eventPerformance = event.getPerformanceByNumber(performanceNumber);
//        assert (eventPerformance == null): "Performance can't be null";
//        TicketedEvent ticketedEvent = (TicketedEvent) event;
//        assert (ticketedEvent.getStatus() != EventStatus.ACTIVE): "Current event is not active";
//        assert (eventPerformance.getEndDateTime().isBefore(LocalDateTime.now())): "Booked event is already over";
//        EntertainmentProviderSystem system = ticketedEvent.getOrganiser().getProviderSystem();
//        assert (system.getNumTicketsLeft(eventNumber, performanceNumber) < numTicketsRequested): "No enough tickets left";

        if (!(user instanceof Consumer)) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_USER_NOT_CONSUMER);
            return;
        }

        if (event == null) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_EVENT_NOT_FOUND);
            return;
        }

        if (!(event instanceof TicketedEvent)) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_NOT_A_TICKETED_EVENT);
            return;
        }

        if (!(numTicketsRequested >= 1)) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_INVALID_NUM_TICKETS);
            return;
        }

        EventPerformance eventPerformance = event.getPerformanceByNumber(performanceNumber);

        if (eventPerformance == null) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_PERFORMANCE_NOT_FOUND);
            return;
        }

        TicketedEvent ticketedEvent = (TicketedEvent) event;

        if (ticketedEvent.getStatus() != EventStatus.ACTIVE) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_EVENT_NOT_ACTIVE);
            return;
        }

        EntertainmentProviderSystem system = ticketedEvent.getOrganiser().getProviderSystem();
        if (eventPerformance.getEndDateTime().isBefore(LocalDateTime.now())) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_ALREADY_OVER);
            return;
        }

        if (system.getNumTicketsLeft(eventNumber, performanceNumber) < numTicketsRequested) {
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_NOT_ENOUGH_TICKETS_LEFT);
            return;
        }

        // After checking, we create new booking using the current user information
        double transactionAmount = ticketedEvent.getDiscountedTicketPrice() * numTicketsRequested;
        paymentSuccess = paymentSystem.processPayment(user.getPaymentAccountEmail(), event.getOrganiser().getPaymentAccountEmail(), transactionAmount);

        IBookingState bookingState = context.getBookingState();
        Booking newBooking = bookingState.createBooking((Consumer) user, eventPerformance, numTicketsRequested, transactionAmount);
        bookingNumberResult = newBooking.getBookingNumber();

        if (!paymentSuccess) {
            newBooking.cancelPaymentFailed();
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_PAYMENT_FAILED);
        }
        else {
            system.recordNewBooking(eventNumber, performanceNumber, bookingNumberResult, ((Consumer) user).getName(), user.getEmail(), numTicketsRequested);
            ((Consumer) user).addBooking(newBooking);
            Logger.getInstance().logAction("BookEventCommand", LogStatus.BOOK_EVENT_SUCCESS);
        }
    }

    @Override
    public Long getResult() {
        return bookingNumberResult;
    }

}