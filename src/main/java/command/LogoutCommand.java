package command;

import controller.Context;
import logging.Logger;
import model.User;
import state.IUserState;

public class LogoutCommand implements ICommand {
    enum LogStatus {
        USER_LOGOUT_SUCCESS,
        USER_LOGOUT_NOT_LOGGED_IN
    }
    public LogoutCommand() {
    }

    @Override
    public void execute(Context context) {
        IUserState userState = context.getUserState();
        User user = userState.getCurrentUser();
        // Condition checks
        if (user == null) {
            Logger.getInstance().logAction("LogoutCommand", LogStatus.USER_LOGOUT_NOT_LOGGED_IN);
            return;
        }
        // Log out the current user by setting it to null
        userState.setCurrentUser(null);

        Logger.getInstance().logAction("LogoutCommand", LogStatus.USER_LOGOUT_SUCCESS);
    }

    @Override
    public Void getResult() {
        return null;
    }

}