package command;

public abstract class UpdateProfileCommand extends Object implements ICommand {

    protected Boolean successResult() {

    };

    public UpdateProfileCommand() {

    };

    protected boolean isProfileUpdateInvalid(Context context, String oldPassword, String newEmail) {

    };

    @Override
    public Boolean getResult() {

    };

}