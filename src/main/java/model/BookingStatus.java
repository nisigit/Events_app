package model;

public enum BookingStatus extends Enum<BookingStatus> {

    public static final BookingStatus Active() {

    };

    public static final BookingStatus CancelledByConsumer() {

    };

    public static final BookingStatus CancelledByProvider() {

    };

    public static final BookingStatus PaymentFailed() {

    };

    public static BookingStatus values() {

    };

    public static BookingStatus valueOf(String name) {

    };

}