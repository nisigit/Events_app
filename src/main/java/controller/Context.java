package controller;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

public class Context {

    private PaymentSystem paymentSystem;
    private IBookingState bookingState;
    private IEventState eventState;
    private ISponsorshipState sponsorshipState;
    private IUserState userState;

    public Context() {
        this.paymentSystem = new MockPaymentSystem();
        this.bookingState = new BookingState();
        this.eventState = new EventState();
        this.sponsorshipState = new SponsorshipState();
        this.userState = new UserState();
    };

    public Context(Context other) {
        new Context();
        this.paymentSystem = other.paymentSystem;
        this.bookingState = other.bookingState;
        this.eventState = other.eventState;
        this.sponsorshipState = other.sponsorshipState;
        this.userState = other.userState;
    };

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    };

    public IUserState getUserState() {
        return userState;
    };

    public IBookingState getBookingState() {
        return bookingState;
    };

    public IEventState getEventState() {
        return eventState;
    };

    public ISponsorshipState getSponsorshipState() {
        return sponsorshipState;
    };

}