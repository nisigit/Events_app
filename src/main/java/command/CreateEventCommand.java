package command;

public abstract class CreateEventCommand extends Object implements ICommand {

    protected final String title() {

    };

    protected final EventType type() {

    };

    protected Long eventNumberResult() {

    };

    public CreateEventCommand(String title, EventType type) {

    };

    protected boolean isUserAllowedToCreateEvent(Context context) {

    };

    @Override
    public Long getResult() {

    };

}