package command;

import controller.Context;
import model.Consumer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterConsumerCommand implements ICommand {

    private String name;
    private String email;
    private String phoneNumber;
    private String
    public RegisterConsumerCommand(String name, String email, String phoneNumber, String password, String paymentAccountEmail)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        Consumer consumer = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
        this.email = email;
    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public Consumer getResult() {

    };

}