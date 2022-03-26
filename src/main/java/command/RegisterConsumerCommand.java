package command;

import controller.Context;
import model.Consumer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterConsumerCommand implements ICommand {

    // TODO: What are we supposed to do with this consumer instance? How does it "login".
    public RegisterConsumerCommand(String name, String email, String phoneNumber, String password, String paymentAccountEmail)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        super();
        Consumer consumer = new Consumer(name, email, phoneNumber, password, paymentAccountEmail);
    };

    // TODO: No idea what this is.
    @Override
    public void execute(Context context) {

    };

    @Override
    public Consumer getResult() {

    };

}