package state;

import model.User;

public class UserState implements IUserState {

    public UserState() {

    };

    public UserState(IUserState other) {
        new UserState();
    };

    @Override
    public void addUser(User user) {

    };

    @Override
    public User getAllUsers() {

    };

    @Override
    public User getCurrentUser() {

    };

    @Override
    public void setCurrentUser(User user) {

    };

}