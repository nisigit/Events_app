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
    private LocalDateTime startDateTime, endDateTime;
    private List<String> performerNames;
    private boolean hasSocialDistancing, hasAirFiltration, isOutdoors;
    private int capacityLimit, venueSize;
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

//        assert (user != null) : "No user logged in";
//        assert (startDateTime.isBefore(endDateTime)): "Provided start date is after end date";
//        assert (capacityLimit >= 1): "Capacity limit cannot be less than 1";
//        assert (venueSize >= 1): "Venue size cannot be less than 1";

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

        for (EventPerformance ep : event.getPerformances()) {
            if (ep.getEvent().getTitle().equals(event.getTitle())) {
                if (ep.getStartDateTime().equals(startDateTime) && ep.getEndDateTime().equals(endDateTime)) {
                    return;
                }
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