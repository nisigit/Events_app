package command;

import controller.Context;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand implements ICommand {

    private List<Booking> result;
    public ListConsumerBookingsCommand() {
    }

    @Override
    public void execute(Context context) {
        this.result = null;
        User user = context.getUserState().getCurrentUser();
        if (user == null) return;
        if (user instanceof Consumer) {
            this.result = ((Consumer) user).getBookings();
        }
    }

    @Override
    public List<Booking> getResult() {
        return result;
    };

}