package command;

import controller.Context;
import model.Event;
import model.EventPerformance;
import model.TicketedEvent;

import controller.Context;

public class GetAvailablePerformanceTicketsCommand implements ICommand {

    private long eventNumber, performanceNumber;
    private int result;
    public GetAvailablePerformanceTicketsCommand(long eventNumber, long performanceNumber) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.result = -1;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        Event event = context.getEventState().findEventByNumber(this.eventNumber);

        if (event == null) return;

        if (event instanceof TicketedEvent) {
            EventPerformance performance = event.getPerformanceByNumber(this.performanceNumber);

            if (performance == null) return;

            // get the tickets left after all the conditions are met
            result = event.getOrganiser().getProviderSystem().getNumTicketsLeft(this.eventNumber, this.performanceNumber);
        }
        else return;
    }

    @Override
    public Integer getResult() {
        return result;
    }

}