package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.User;
import state.IEventState;

import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand implements ICommand {

    private long eventNumber;
    private String venueAddress;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<String> performerNames;
    private boolean hasSocialDistancing;
    private boolean hasAirFiltration;
    private boolean isOutdoors;
    private int capacityLimit;
    private int venueSize;
    private EventPerformance newPerformance;


    public AddEventPerformanceCommand(long eventNumber, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize) {
        this.eventNumber = eventNumber;
        this.venueAddress = venueAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.performerNames = performerNames;
        this.hasAirFiltration = hasAirFiltration;
        this.hasSocialDistancing = hasSocialDistancing;
        this.isOutdoors = isOutdoors;
        this.capacityLimit = capacityLimit;
        this.venueSize = venueSize;
    }


    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(eventNumber);
        newPerformance = null;

        if (startDateTime.isAfter(endDateTime) ||
                capacityLimit < 1 ||
                venueSize < 1 ||
                !(user instanceof EntertainmentProvider) ||
                event == null) {
            return;
        }

        if (user != event.getOrganiser()) {
            return;
        }

        // TODO: Do we have to compare times for all events with the same title?
        for (EventPerformance ep : event.getPerformances()) {
            if (ep.getStartDateTime().equals(startDateTime) && ep.getEndDateTime().equals(endDateTime)) {
                return;
            }
        }

        IEventState eventState = context.getEventState();
        newPerformance = eventState.createEventPerformance(event, venueAddress, startDateTime, endDateTime, performerNames, hasSocialDistancing, hasAirFiltration, isOutdoors, capacityLimit, venueSize);
        event.addPerformance(newPerformance);
    }

    @Override
    public EventPerformance getResult() {
        return newPerformance;
    }

}