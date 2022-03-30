package command;

import controller.Context;
import model.User;

public abstract class UpdateProfileCommand implements ICommand {

    protected Boolean successResult;

    public UpdateProfileCommand() {
    }

    protected boolean isProfileUpdateInvalid(Context context, String oldPassword, String newEmail) {
        User user = context.getUserState().getCurrentUser();
        if (user.checkPasswordMatch(oldPassword)) {
            return !(context.getUserState().getAllUsers().containsKey(newEmail));
        }
        return false;
    }

    @Override
    public Boolean getResult() {
        return successResult;
    }

}