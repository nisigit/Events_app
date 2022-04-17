package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListEventsCommand implements ICommand {
    enum LogStatus {
        LIST_USER_EVENTS_SUCCESS,
        LIST_USER_EVENTS_NOT_LOGGED_IN
    }

    private boolean userEventsOnly, activeEventsOnly;
    private ArrayList<Event> eventListResult;

    public ListEventsCommand(boolean userEventsOnly, boolean activeEventsOnly) {
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
        this.eventListResult = new ArrayList<>();
    }

    private boolean eventSatisfiesPreferences(ConsumerPreferences preferences, Event event) {
        Collection<EventPerformance> performances = event.getPerformances();
        for (EventPerformance ep: performances) {
            if ((ep.hasAirFiltration() == preferences.preferAirFiltration()) &&
                    (ep.isOutdoors() == preferences.preferOutdoorsOnly()) &&
                    (ep.hasSocialDistancing() == preferences.preferSocialDistancing()) &&
                    (ep.getCapacityLimit() <= preferences.preferredMaxCapacity()) &&
                    (ep.getVenueSize() <= preferences.preferredMaxVenueSize())) {
                return true;
            }
        }
        return false;
    }

    private List<Event> filterEvents(List<Event> events, boolean activeEventsOnly) {
        ArrayList<Event> filteredEvents = new ArrayList<>(events);
        if (activeEventsOnly) filteredEvents.removeIf(event -> event.getStatus() != EventStatus.ACTIVE);
        return filteredEvents;
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        User user = context.getUserState().getCurrentUser();
        List<Event> allEvents = context.getEventState().getAllEvents();

        // Condition Checks for userEventsOnly
        // TODO: Class diagram has an extra class which we might need to implement.
        if (this.userEventsOnly) {
            if (user == null) {
                logger.logAction("ListEventsCommand", LogStatus.LIST_USER_EVENTS_NOT_LOGGED_IN);
                return;
            }
            if (user instanceof EntertainmentProvider) {
                List<Event> entEvents = ((EntertainmentProvider) user).getEvents();
                eventListResult = new ArrayList<>(entEvents);
            }
            else if (user instanceof Consumer) {
                ConsumerPreferences cp = ((Consumer) user).getPreferences();
                for (Event event : allEvents) {
                    if (eventSatisfiesPreferences(cp, event)) {
                        eventListResult.add(event);
                    }
                }
            }
        }
        else eventListResult = new ArrayList<>(allEvents);

        eventListResult = (ArrayList<Event>) filterEvents(eventListResult, activeEventsOnly);

        Logger.getInstance().logAction("ListEventsCommand", LogStatus.LIST_USER_EVENTS_SUCCESS);
    }

    @Override
    public List<Event> getResult() {
        return eventListResult;
    }
}
