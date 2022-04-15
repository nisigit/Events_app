package command;

import controller.Context;
import logging.Logger;
import model.User;

public class LoginCommand implements ICommand {

    private String email, password;
    private User userResult;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
        userResult = null;
    }

    @Override
    public void execute(Context context) {
        userResult = context.getUserState().getAllUsers().get(email);

        // Condition checks
        if (userResult == null || !userResult.checkPasswordMatch(password)) {
            return;
        }

        // If everything passes, log in the current user
        if (userResult != null) {
            context.getUserState().setCurrentUser(userResult);
        }

        Logger.getInstance().logAction("LoginCommand", userResult);
    }

    @Override
    public User getResult() {
        return userResult;
    }

}