package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.*;

import java.util.Map;

public class RegisterConsumerCommand implements ICommand {

    private String name, email, phoneNumber, password, paymentAccountEmail;
    private Consumer consumer;

    public RegisterConsumerCommand(String name, String email, String phoneNumber, String password, String paymentAccountEmail) {
        Consumer consumer = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
        this.consumer = consumer;
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
            consumer = null;
            return;
        }
        if (allUsers.containsKey(email)) {
            consumer = null;
            return;
        }
        if (consumer != null) {
            // If successfully registered, automatically log the current user in
            context.getUserState().setCurrentUser(consumer);
            context.getUserState().addUser(consumer);
        }

        Logger.getInstance().logAction("RegisterConsumerCommand", consumer);
    }

    @Override
    public Consumer getResult() {
        return consumer;
    }

}