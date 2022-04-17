package command;

import controller.Context;
import model.User;

import logging.Logger;

public abstract class UpdateProfileCommand implements ICommand {
    enum LogStatus {
        USER_UPDATE_PROFILE_NOT_LOGGED_IN,
        USER_UPDATE_PROFILE_WRONG_PASSWORD,
        USER_UPDATE_PROFILE_EMAIL_ALREADY_IN_USE
    }
    protected Boolean successResult;

    public UpdateProfileCommand() {
    }

    protected boolean isProfileUpdateInvalid(Context context, String oldPassword, String newEmail) {
        Logger logger = Logger.getInstance();
        User user = context.getUserState().getCurrentUser();

        // Condition checks

        // Using assertions to check conditions

//        assert (user == null): "Current user is not logged in";
//        assert (!(user.checkPasswordMatch(oldPassword))): "Wrong password";
//        assert (context.getUserState().getAllUsers().containsKey(newEmail)): "Email already registered";

        if (user == null) {
            logger.logAction("UpdateProfileCommand", LogStatus.USER_UPDATE_PROFILE_NOT_LOGGED_IN);
            return false;
        }
        // Check if the update valid by checking if the newEmail exists.
        if (user.checkPasswordMatch(oldPassword)) {
            if (!(context.getUserState().getAllUsers().containsKey(newEmail))) {
                return true;
            }
            else {
                logger.logAction("UpdateProfileCommand", LogStatus.USER_UPDATE_PROFILE_EMAIL_ALREADY_IN_USE);
                return false;
            }
        }
        else {
            logger.logAction("UpdateProfileCommand", LogStatus.USER_UPDATE_PROFILE_WRONG_PASSWORD);
            return false;
        }

    }

    @Override
    public Boolean getResult() {
        return successResult;
    }

}