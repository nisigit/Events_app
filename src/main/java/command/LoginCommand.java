package command;

import controller.Context;
import model.User;

public class LoginCommand implements ICommand {

    private String email;
    private String password;
    private User user;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
        user = null;
    }

    @Override
    public void execute(Context context) {
        user = context.getUserState().getAllUsers().get(email);

        if (user == null) {
            return;
        }

        if (!user.checkPasswordMatch(password)) {
            user = null;
        }
    }

    @Override
    public User getResult() {
        return user;
    }

}