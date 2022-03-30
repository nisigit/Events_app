package command;

import controller.Context;
import model.Booking;

import java.time.LocalDateTime;

public class GovernmentReport1Command implements ICommand {

    private Booking result;
    private LocalDateTime intervalStartInclusive;
    private LocalDateTime intervalEndInclusive;

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {
        this.intervalEndInclusive = intervalEndInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public Booking getResult() {
        return result;
    };

}