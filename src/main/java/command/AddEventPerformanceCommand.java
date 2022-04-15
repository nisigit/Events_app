package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.User;
import state.IEventState;

import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand implements ICommand {

    enum LogStatus{
        ADD_PERFORMANCE_SUCCESS,
        ADD_PERFORMANCE_START_AFTER_END,
        ADD_PERFORMANCE_CAPACITY_LESS_THAN_1,
        ADD_PERFORMANCE_VENUE_SIZE_LESS_THAN_1,
        ADD_PERFORMANCE_EVENTS_WITH_SAME_TITLE_CLASH,
        ADD_PERFORMANCE_USER_NOT_LOGGED_IN,
        ADD_PERFORMANCE_USER_NOT_ENTERTAINMENT_PROVIDER,
        ADD_PERFORMANCE_EVENT_NOT_FOUND,
        ADD_PERFORMANCE_USER_NOT_EVENT_ORGANISER,
    }

    private long eventNumber;
    private String venueAddress;
    private LocalDateTime startDateTime, endDateTime;
    private List<String> performerNames;
    private boolean hasSocialDistancing, hasAirFiltration, isOutdoors;
    private int capacityLimit, venueSize;
    private EventPerformance eventPerformanceResult;


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
        eventPerformanceResult = null;

        // Using assertions to check conditions
//        assert (user != null) : "No user logged in";
//        assert (startDateTime.isAfter(endDateTime)): "Provided start date is after end date";
//        assert (capacityLimit < 1): "Capacity limit cannot be less than 1";
//        assert (venueSize < 1): "Venue size cannot be less than 1";

        // conditional checks as specified in docs - abort execution if a condition is satisfied
        if (user == null) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_USER_NOT_LOGGED_IN);
            return;
        }

        if (startDateTime.isAfter(endDateTime)) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_START_AFTER_END);
            return;
        }

        if (capacityLimit < 1) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_CAPACITY_LESS_THAN_1);
            return;
        }

        if (venueSize < 1) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_VENUE_SIZE_LESS_THAN_1);
            return;
        }

        if (!(user instanceof EntertainmentProvider)) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_USER_NOT_ENTERTAINMENT_PROVIDER);
            return;
        }

        if (event == null) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_EVENT_NOT_FOUND);
            return;
        }

        if (!user.equals(event.getOrganiser())) {
            Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_USER_NOT_EVENT_ORGANISER);
            return;
        }

        // checks if an identical performance already exists for this event
        for (EventPerformance ep : event.getPerformances()) {
            if (ep.getEvent().getTitle().equals(event.getTitle())) {
                if (ep.getStartDateTime().equals(startDateTime) && ep.getEndDateTime().equals(endDateTime)) {
                    Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_EVENTS_WITH_SAME_TITLE_CLASH);
                    return;
                }
            }
        }

        // After checking, add the performance to the corresponding event
        IEventState eventState = context.getEventState();
        eventPerformanceResult = eventState.createEventPerformance(event, venueAddress, startDateTime, endDateTime, performerNames, hasSocialDistancing, hasAirFiltration, isOutdoors, capacityLimit, venueSize);
        event.addPerformance(eventPerformanceResult);
        event.getOrganiser().getProviderSystem().recordNewPerformance(this.eventNumber, eventPerformanceResult.getPerformanceNumber(),
                startDateTime, endDateTime);

        Logger.getInstance().logAction("AddEventPerformanceCommand", LogStatus.ADD_PERFORMANCE_SUCCESS);
    }

    @Override
    public EventPerformance getResult() {
        return eventPerformanceResult;
    }

}