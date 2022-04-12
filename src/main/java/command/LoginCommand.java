package command;

import controller.Context;
import logging.Logger;
import model.User;

public class LoginCommand implements ICommand {

    private String email, password;
    private User user;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
        user = null;
    }

    @Override
    public void execute(Context context) {
        user = context.getUserState().getAllUsers().get(email);

        // Condition checks
        if (user == null || !user.checkPasswordMatch(password)) {
            return;
        }

        // If everything passes, log in the current user
        if (user != null) {
            context.getUserState().setCurrentUser(user);
        }

        Logger.getInstance().logAction("LoginCommand", user);
    }

    @Override
    public User getResult() {
        return user;
    }

}