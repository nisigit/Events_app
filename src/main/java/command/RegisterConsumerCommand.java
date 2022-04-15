package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.*;

import java.util.Map;

public class RegisterConsumerCommand implements ICommand {

    private String name, email, phoneNumber, password, paymentAccountEmail;
    private Consumer newConsumerResult;

    public RegisterConsumerCommand(String name, String email, String phoneNumber, String password, String paymentAccountEmail) {
        Consumer consumer = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
        this.newConsumerResult = consumer;
    }

    @Override
    public void execute(Context context) {
        Map<String, User> allUsers = context.getUserState().getAllUsers();
        // Checking all conditions
        if ((email == null) ||
                (name == null) ||
                (phoneNumber == null) ||
                (password == null) ||
                (paymentAccountEmail == null)) {
            newConsumerResult = null;
            return;
        }
        if (allUsers.containsKey(email)) {
            newConsumerResult = null;
            return;
        }
        if (newConsumerResult != null) {
            // If successfully registered, automatically log the current user in
            context.getUserState().setCurrentUser(newConsumerResult);
            context.getUserState().addUser(newConsumerResult);
        }

        Logger.getInstance().logAction("RegisterConsumerCommand", newConsumerResult);
    }

    @Override
    public Consumer getResult() {
        return newConsumerResult;
    }

}