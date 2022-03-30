package command;

import controller.Context;
import model.*;

import java.util.List;

public class ListEventsCommand implements ICommand {

    private boolean userEventsOnly;
    private boolean activeEventsOnly;
    private List<Event> result;

    public ListEventsCommand(boolean userEventsOnly, boolean activeEventsOnly) {
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
    }

    @Override
    public void execute(Context context) {
        // Sam: changed implementation a bit - tidied up some indentation and also amended activeEventsOnly case
        // seemed to me as though with the way it was originally implemented, result would have non-user events
        // even if userEventsOnly was true

        User user = context.getUserState().getCurrentUser();
        List<Event> allEvents = context.getEventState().getAllEvents();

        if (user == null) return;
        if (this.userEventsOnly) {
            if (user instanceof EntertainmentProvider) {
                result = ((EntertainmentProvider) user).getEvents();
            }
            else if (user instanceof Consumer) {
                ConsumerPreferences cp = ((Consumer) user).getPreferences();
                for (Event event : allEvents) {
                    EventPerformance ep = event.getPerformances().iterator().next();
                    if ((ep.hasAirFiltration() == cp.preferAirFiltration()) &&
                            (ep.isOutdoors() == cp.preferAirFiltration()) &&
                            (ep.hasSocialDistancing() == cp.preferSocialDistancing()) &&
                            (ep.getCapacityLimit() <= cp.preferredMaxCapacity()) &&
                            (ep.getVenueSize() <= cp.preferredMaxVenueSize())) {
                        result.add(event);
                    }
                }
            }
        }
        else result = allEvents;

        if (this.activeEventsOnly) {
            result.removeIf(event -> event.getStatus() != EventStatus.ACTIVE);
        }

//            if (this.userEventsOnly && (user == null)) return;
//            else if (this.userEventsOnly && (user instanceof EntertainmentProvider)) {
//                result = ((EntertainmentProvider) user).getEvents();
//            } else if (this.userEventsOnly && (user instanceof Consumer)) {
//                ConsumerPreferences cp = ((Consumer) user).getPreferences();
//                for (Event event : allEvents) {
//                    EventPerformance ep = event.getPerformances().iterator().next();
//                    if ((ep.hasAirFiltration() == cp.preferAirFiltration()) &&
//                            (ep.isOutdoors() == cp.preferAirFiltration()) &&
//                            (ep.hasSocialDistancing() == cp.preferSocialDistancing()) &&
//                            (ep.getCapacityLimit() <= cp.preferredMaxCapacity()) &&
//                            (ep.getVenueSize() <= cp.preferredMaxVenueSize())) {
//                        result.add(event);
//                    } else {
//                        return;
//                    }
//                }
//                if (this.activeEventsOnly) {
//                    for (Event event : allEvents) {
//                        if (event.getStatus() == EventStatus.ACTIVE) {
//                            result.add(event);
//                        }
//                    }
//                }
//            }
//        }
    }

    @Override
    public List<Event> getResult() {
        return result;
    }
}
