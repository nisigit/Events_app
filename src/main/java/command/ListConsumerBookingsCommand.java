package command;

import controller.Context;
import logging.Logger;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand implements ICommand {
    enum LogStatus {
        LIST_CONSUMER_BOOKINGS_NOT_LOGGED_IN,
        LIST_CONSUMER_BOOKINGS_USER_NOT_CONSUMER,
        LIST_CONSUMER_BOOKINGS_SUCCESS
    }
    private List<Booking> bookingListResult;
    public ListConsumerBookingsCommand() {
        this.bookingListResult = null;
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        // Condition checks
        User user = context.getUserState().getCurrentUser();
        if (user == null) {
            logger.logAction("ListConsumerBookingsCommand", LogStatus.LIST_CONSUMER_BOOKINGS_NOT_LOGGED_IN);
            return;
        }
        if (user instanceof Consumer) {
            this.bookingListResult = ((Consumer) user).getBookings();
            logger.logAction("ListConsumerBookingsCommand", LogStatus.LIST_CONSUMER_BOOKINGS_SUCCESS);
        }
        else logger.logAction("ListConsumerBookingsCommand", LogStatus.LIST_CONSUMER_BOOKINGS_USER_NOT_CONSUMER);
    }

    @Override
    public List<Booking> getResult() {
        return bookingListResult;
    };

}