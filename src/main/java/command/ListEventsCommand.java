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
        result = null;
        User user = context.getUserState().getCurrentUser();
        List<Event> allEvents = context.getEventState().getAllEvents();

        if (this.userEventsOnly && (user == null)) return;
        else if (this.userEventsOnly && (user instanceof EntertainmentProvider)) {
            result = ((EntertainmentProvider) user).getEvents();
        } else if (this.userEventsOnly && (user instanceof Consumer)) {
            ConsumerPreferences cp = ((Consumer) user).getPreferences();
            for (Event event : allEvents) {
                EventPerformance ep = event.getPerformances().iterator().next();
                if ((ep.hasAirFiltration() == cp.preferAirFiltration()) &&
                        (ep.isOutdoors() == cp.preferAirFiltration()) &&
                        (ep.hasSocialDistancing() == cp.preferSocialDistancing()) &&
                        (ep.getCapacityLimit() <= cp.preferredMaxCapacity()) &&
                        (ep.getVenueSize() <= cp.preferredMaxVenueSize())) {
                    result.add(event);
                } else {
                    return;
                }
            }
            if (this.activeEventsOnly) {
                for (Event event : allEvents) {
                    if (event.getStatus() == EventStatus.ACTIVE) {
                        result.add(event);

                    }
                }
            }
        }
    }

        @Override
        public List<Event> getResult() {
            return result;
        }


}
