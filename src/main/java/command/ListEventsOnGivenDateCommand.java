package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class ListEventsOnGivenDateCommand extends ListEventsCommand {

    private boolean userEventsOnly, activeEventsOnly;
    private List<Event> result;
    private LocalDateTime searchDateTime;

    public ListEventsOnGivenDateCommand(boolean userEventsOnly, boolean activeEventsOnly, LocalDateTime searchDateTime) {
        super(userEventsOnly, activeEventsOnly);
        this.searchDateTime = searchDateTime;
    };

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if (user == null) return;

    };

    @Override
    public List<Event> getResult() {

    };

}