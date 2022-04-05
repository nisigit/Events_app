package command;

import controller.Context;
import model.User;
import state.IUserState;

public class LogoutCommand implements ICommand {

    public LogoutCommand() {

    }

    @Override
    public void execute(Context context) {
        IUserState userState = context.getUserState();
        User user = userState.getCurrentUser();
        // Condition checks
        if (user == null) {
            return;
        }
        // Log out the current user by setting it to null
        userState.setCurrentUser(null);
    }

    @Override
    public Void getResult() {
        return null;
    }

}