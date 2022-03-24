package state;

import model.User;

public interface IUserState {

    void addUser(User user);

    User getAllUsers();

    User getCurrentUser();

    void setCurrentUser(User user);

}