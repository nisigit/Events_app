package command;

import controller.Context;
import model.Event;

import java.time.LocalDateTime;

public class ListEventsOnGivenDateCommand extends ListEventsCommand {

    public ListEventsOnGivenDateCommand(boolean userEventsOnly, boolean activeEventsOnly, LocalDateTime searchDateTime) {
        super();

    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public Event getResult() {

    };

}