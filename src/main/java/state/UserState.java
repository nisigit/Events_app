package state;

import command.LoginCommand;
import model.GovernmentRepresentative;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserState implements IUserState {

    private HashMap<String, User> allUsers;
    private User currentUser;

    public UserState() {
        allUsers = new HashMap<>();
        currentUser = null;
        registerGovtReps();
    }

    public UserState(IUserState other) {
        new UserState();
        this.allUsers = (HashMap<String, User>) other.getAllUsers();
        this.currentUser = other.getCurrentUser();
    }

    @Override
    public void addUser(User user) {
        allUsers.put(user.getEmail(), user);
    }

    @Override
    public Map<String, User> getAllUsers() {
        return this.allUsers;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void registerGovtReps() {
        GovernmentRepresentative rep = new GovernmentRepresentative("margaret.thatcher@gov.uk", "The Good times  ", "margaret.thatcher@gov.uk");
        addUser(rep);
    }
}