package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

public class ListEventsOnGivenDateCommand extends ListEventsCommand {

    private List<Event> eventListResult = new ArrayList<>();
    private LocalDateTime searchDateTime;

    public ListEventsOnGivenDateCommand(boolean userEventsOnly, boolean activeEventsOnly, LocalDateTime searchDateTime) {
        super(userEventsOnly, activeEventsOnly);
        this.searchDateTime = searchDateTime;
    }

    @Override
    public void execute(Context context) {
        // Getting the result of the super class listing events
        super.execute(context);
        List<Event> events = super.getResult();

        for (Event event : events) {
            // Condition checks
            boolean isIncluded = false;
            Collection<EventPerformance> ePerformances = event.getPerformances();
            for (EventPerformance performance : ePerformances) {
                Duration startDiff = Duration.between(performance.getStartDateTime(), searchDateTime);
                Duration endDiff = Duration.between(searchDateTime, performance.getEndDateTime());
                if (startDiff.toHours() <= 24 && endDiff.toHours() <= 24) {
                    isIncluded = true;
                    break;
                }
            }
            // if the condition passed, add the event to the output list
            if (isIncluded) eventListResult.add(event);
        }

        Logger.getInstance().logAction("ListEventsOnGivenDateCommand", eventListResult);
    }

    @Override
    public List<Event> getResult() {
        return eventListResult;
    }

}