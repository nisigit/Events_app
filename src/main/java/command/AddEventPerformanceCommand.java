package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventPerformance;

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

    // TODO: Why is this error?


    @Override
    public void execute(Context context) {
        boolean result = this.startDateTime.isBefore(this.endDateTime) &&
                this.capacityLimit >= 1 &&
                this.venueSize >= 1 &&
                context.getUserState().getCurrentUser() instanceof EntertainmentProvider &&
                context.getEventState().findEventByNumber(this.eventNumber) != null;
    }

    @Override
    public EventPerformance getResult() {

    }

}