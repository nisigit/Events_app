package command;

import controller.Context;
import logging.Logger;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand implements ICommand {

    private List<Booking> bookingListResult;
    public ListConsumerBookingsCommand() {
        this.bookingListResult = null;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        User user = context.getUserState().getCurrentUser();
        if (user == null) return;
        if (user instanceof Consumer) {
            this.bookingListResult = ((Consumer) user).getBookings();
        }

        Logger.getInstance().logAction("ListConsumerBookingsCommand", bookingListResult);
    }

    @Override
    public List<Booking> getResult() {
        return bookingListResult;
    };

}