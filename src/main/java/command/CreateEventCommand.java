package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import model.User;

public abstract class CreateEventCommand implements ICommand {

    enum LogStatus{
        CREATE_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER,
        CREATE_EVENT_USER_NOT_LOGGED_IN
    }

    protected Long eventNumberResult;
    protected String title;
    protected EventType type;

    public CreateEventCommand(String title, EventType type) {
        this.title = title;
        this.type = type;
        eventNumberResult = null;
    }

    @Override
    public Long getResult() {
        return eventNumberResult;
    }

    // Check if the current user is an entertainment provider
    protected boolean isUserAllowedToCreateEvent(Context context) {
        User currentUser = context.getUserState().getCurrentUser();

        // Using assertions to check conditions

//        assert (currentUser == null): "Current user is not logged in";
//        assert (!(currentUser instanceof EntertainmentProvider)): "Current is not an entertainment provider";

        if (currentUser == null) {
            Logger.getInstance().logAction("CreateEventCommand", LogStatus.CREATE_EVENT_USER_NOT_LOGGED_IN);
            return false;
        }
        if (!(currentUser instanceof EntertainmentProvider)) {
            Logger.getInstance().logAction("CreateEventCommand", LogStatus.CREATE_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER);
            return false;
        }
        else return true;
    }
}