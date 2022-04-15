package command;

import controller.Context;
import logging.Logger;
import model.User;

public class LoginCommand implements ICommand {
    enum LogStatus {
        USER_LOGIN_SUCCESS,
        USER_LOGIN_EMAIL_NOT_REGISTERED,
        USER_LOGIN_WRONG_PASSWORD
    }

    private String email, password;
    private User userResult;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
        userResult = null;
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        userResult = context.getUserState().getAllUsers().get(email);

        // Condition checks
        if (userResult == null) {
            logger.logAction("LoginCommand", LogStatus.USER_LOGIN_EMAIL_NOT_REGISTERED);
            return;
        }
        if (!userResult.checkPasswordMatch(password)) {
            logger.logAction("LoginCommand", LogStatus.USER_LOGIN_WRONG_PASSWORD);
            return;
        }

        // If everything passes, log in the current user
        if (userResult != null) {
            context.getUserState().setCurrentUser(userResult);
            logger.logAction("LoginCommand", LogStatus.USER_LOGIN_SUCCESS);
        }
    }

    @Override
    public User getResult() {
        return userResult;
    }

}