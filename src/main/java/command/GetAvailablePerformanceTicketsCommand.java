package command;

import controller.Context;
import logging.Logger;
import model.Event;
import model.EventPerformance;
import model.TicketedEvent;

import controller.Context;

public class GetAvailablePerformanceTicketsCommand implements ICommand {
    enum LogStatus {
        GET_AVAILABLE_TICKETS_SUCCESS,
        GET_AVAILABLE_TICKETS_EVENT_NOT_FOUND,
        GET_AVAILABLE_TICKETS_NOT_TICKETED_EVENT,
        GET_AVAILABLE_TICKETS_PERFORMANCE_NOT_FOUND
    }

    private long eventNumber, performanceNumber;
    private int result;
    public GetAvailablePerformanceTicketsCommand(long eventNumber, long performanceNumber) {
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.result = -1;
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        // Condition checks
        Event event = context.getEventState().findEventByNumber(this.eventNumber);

        // Using assertions to check conditions

//        assert (event == null): "The event can't be null";
//        if (event instanceof TicketedEvent) {
//            EventPerformance performance = event.getPerformanceByNumber(this.performanceNumber);
//
//            assert (performance == null) : "The performance is not found";
//        }


        if (event == null) {
            logger.logAction("GetAvailablePerformanceTicketsCommand", LogStatus.GET_AVAILABLE_TICKETS_EVENT_NOT_FOUND);
            return;
        }

        if (event instanceof TicketedEvent) {
            EventPerformance performance = event.getPerformanceByNumber(this.performanceNumber);

            if (performance == null) {
                logger.logAction("GetAvailablePerformanceTicketsCommand", LogStatus.GET_AVAILABLE_TICKETS_PERFORMANCE_NOT_FOUND);
                return;
            }

            // get the tickets left after all the conditions are met
            result = event.getOrganiser().getProviderSystem().getNumTicketsLeft(this.eventNumber, this.performanceNumber);
            logger.logAction("GetAvailablePerformanceTicketsCommand", LogStatus.GET_AVAILABLE_TICKETS_SUCCESS);
        }
        else logger.logAction("GetAvailablePerformanceTicketsCommand", LogStatus.GET_AVAILABLE_TICKETS_NOT_TICKETED_EVENT);

        //Logger.getInstance().logAction("GetAvailablePerformanceTicketsCommand", result);
    }

    @Override
    public Integer getResult() {
        return result;
    }

}