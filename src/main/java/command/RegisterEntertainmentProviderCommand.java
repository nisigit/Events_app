package command;

import controller.Context;
import model.EntertainmentProvider;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class RegisterEntertainmentProviderCommand implements ICommand {

    public RegisterEntertainmentProviderCommand(String orgName, String orgAddress, String paymentAccountEmail, String mainRepName, String mainRepEmail, String password, List<String> otherRepNames, List<String> otherRepEmails) throws InvalidKeySpecException, NoSuchAlgorithmException {
        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password, otherRepNames, otherRepEmails);
    };

    @Override
    public void execute(Context context) {

    };

    @Override
    public EntertainmentProvider getResult() {

    };

}