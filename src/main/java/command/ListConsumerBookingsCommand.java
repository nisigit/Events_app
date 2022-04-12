package command;

import controller.Context;
import logging.Logger;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand implements ICommand {

    private List<Booking> result;
    public ListConsumerBookingsCommand() {
        this.result = null;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        User user = context.getUserState().getCurrentUser();
        if (user == null) return;
        if (user instanceof Consumer) {
            this.result = ((Consumer) user).getBookings();
        }

        Logger.getInstance().logAction("ListConsumerBookingsCommand", result);
    }

    @Override
    public List<Booking> getResult() {
        return result;
    };

}