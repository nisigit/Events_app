package command;

import controller.Context;

public class CancelEventCommand implements ICommand {
    private long eventNumber;
    private String organiserMessage;

    public CancelEventCommand(long eventNumber, String organiserMessage) {
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public Boolean getResult() {

    };

}