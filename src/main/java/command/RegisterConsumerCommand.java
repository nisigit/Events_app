package command;

import controller.Context;
import model.Consumer;
import model.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class RegisterConsumerCommand implements ICommand {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String paymentAccountEmail;
    private Consumer consumer;

    public RegisterConsumerCommand(String name, String email, String phoneNumber, String password, String paymentAccountEmail)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        Consumer consumer = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
        this.consumer = consumer;
    };

    @Override
    public void execute(Context context) {
        Map<String, User> allUsers = context.getUserState().getAllUsers();
        if ((email == null) ||
                (name == null) ||
                (phoneNumber == null) ||
                (password == null) ||
                (paymentAccountEmail == null)) {
            consumer = null;
        }
        if (allUsers.containsKey(email)) {
            consumer = null;
        }
    };

    @Override
    public Consumer getResult() {
        return consumer;
    };

}