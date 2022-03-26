package state;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserState implements IUserState {

    private HashMap<String, User> allUsers;
    private User currentUser;

    public UserState() {
        allUsers = new HashMap<String, User>();
        currentUser = null;
    };

    public UserState(IUserState other) {
        new UserState();
        this.allUsers = (HashMap<String, User>) other.getAllUsers();
    };

    @Override
    public void addUser(User user) {
        allUsers.put(user.getEmail(), user);
    };

    @Override
    public Map<String, User> getAllUsers() {
        return this.allUsers;
    };

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    };

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    };

}