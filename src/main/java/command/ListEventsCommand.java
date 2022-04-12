package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListEventsCommand implements ICommand {

    private boolean userEventsOnly, activeEventsOnly;
    private ArrayList<Event> result;

    public ListEventsCommand(boolean userEventsOnly, boolean activeEventsOnly) {
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
        this.result = new ArrayList<>();
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
                result = new ArrayList<>(entEvents);
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
                            result.add(event);
                            break;
                        }
                    }

                }
            }
        }
        else result = new ArrayList<>(allEvents);

        if (this.activeEventsOnly) {
            result.removeIf(event -> event.getStatus() != EventStatus.ACTIVE);
        }

        Logger.getInstance().logAction("ListEventsCommand", result);
    }

    @Override
    public List<Event> getResult() {
        return result;
    }
}
