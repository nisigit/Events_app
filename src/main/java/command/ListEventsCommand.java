package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListEventsCommand implements ICommand {

    private boolean userEventsOnly, activeEventsOnly;
    private ArrayList<Event> eventListResult;

    public ListEventsCommand(boolean userEventsOnly, boolean activeEventsOnly) {
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
        this.eventListResult = new ArrayList<>();
    }

    @Override
    public void execute(Context context) {

        User user = context.getUserState().getCurrentUser();
        List<Event> allEvents = context.getEventState().getAllEvents();

        // Condition Checks for userEventsOnly
        if (this.userEventsOnly) {
            if (user == null) return;
            if (user instanceof EntertainmentProvider) {
                List<Event> entEvents = ((EntertainmentProvider) user).getEvents();
                eventListResult = new ArrayList<>(entEvents);
            }
            else if (user instanceof Consumer) {
                ConsumerPreferences cp = ((Consumer) user).getPreferences();
                for (Event event : allEvents) {
                    Collection<EventPerformance> performances = event.getPerformances();
                    for (EventPerformance ep: performances) {
                        if ((ep.hasAirFiltration() == cp.preferAirFiltration()) &&
                                (ep.isOutdoors() == cp.preferOutdoorsOnly()) &&
                                (ep.hasSocialDistancing() == cp.preferSocialDistancing()) &&
                                (ep.getCapacityLimit() <= cp.preferredMaxCapacity()) &&
                                (ep.getVenueSize() <= cp.preferredMaxVenueSize())) {
                            eventListResult.add(event);
                            break;
                        }
                    }

                }
            }
        }
        else eventListResult = new ArrayList<>(allEvents);

        if (this.activeEventsOnly) {
            eventListResult.removeIf(event -> event.getStatus() != EventStatus.ACTIVE);
        }

        Logger.getInstance().logAction("ListEventsCommand", eventListResult);
    }

    @Override
    public List<Event> getResult() {
        return eventListResult;
    }
}
