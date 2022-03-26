package command;

import controller.Context;
import model.EventType;

public abstract class CreateEventCommand implements ICommand {

    protected Long eventNumberResult;
    protected String title;
    protected EventType type;

    public CreateEventCommand(String title, EventType type) {
        this.title = title;
        this.type = type;
    }

    @Override
    public Long getResult() {
        return eventNumberResult;
    };

    protected boolean isUserAllowedToCreateEvent(Context context) {

    };
}