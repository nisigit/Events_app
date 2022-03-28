package command;

import controller.Context;
import model.Consumer;

public class BookEventCommand implements ICommand {

    private long eventNumber;
    private long performanceNumber;
    private long numTicketsRequested;

    public BookEventCommand(long eventNumber, long performanceNumber, int numTicketsRequested) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    // TODO: Execute command remaining.
    @Override
    public void execute(Context context) {
        boolean result = context.getUserState().getCurrentUser() instanceof Consumer &&
                context.getEventState().findEventByNumber(eventNumber) != null &&
                numTicketsRequested >= 1 &&
                context.getEventState().findEventByNumber(eventNumber).getPerformanceByNumber(performanceNumber) != null &&
    }

    @Override
    public Long getResult() {

    }

}