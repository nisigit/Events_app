package command;

import controller.Context;
import model.EntertainmentProvider;

import java.util.List;

public class RegisterEntertainmentProviderCommand implements ICommand {

    public RegisterEntertainmentProviderCommand(String orgName, String orgAddress, String paymentAccountEmail, String mainRepName, String mainRepEmail, String password, List<String> otherRepNames, List<String> otherRepEmails) {
        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password, otherRepNames, otherRepEmails);
    };

    // TODO: Same as RegisterConsumerCommand.
    @Override
    public void execute(Context context) {

    };

    @Override
    public EntertainmentProvider getResult() {

    };

}