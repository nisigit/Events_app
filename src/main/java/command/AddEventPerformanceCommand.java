package command;

import javax.naming.Context;
import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand extends Object implements ICommand {

    public AddEventPerformanceCommand(long eventNumber, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize) {

    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public EventPerformance getResult() {

    };

}