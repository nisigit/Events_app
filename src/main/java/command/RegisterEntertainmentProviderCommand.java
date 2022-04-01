package command;

import controller.Context;
import model.EntertainmentProvider;
import model.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

public class RegisterEntertainmentProviderCommand implements ICommand {

    private String orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password;
    private List<String> otherRepNames;
    private List<String> otherRepEmails;
    private EntertainmentProvider entertainmentProvider;

    public RegisterEntertainmentProviderCommand(String orgName, String orgAddress, String paymentAccountEmail, String mainRepName, String mainRepEmail, String password, List<String> otherRepNames, List<String> otherRepEmails) {
        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password, otherRepNames, otherRepEmails);
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.paymentAccountEmail = paymentAccountEmail;
        this.mainRepName = mainRepName;
        this.mainRepEmail = mainRepEmail;
        this.password = password;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
        this.entertainmentProvider = entertainmentProvider;
    };

    @Override
    public void execute(Context context) {
        Map<String, User> allUsers = context.getUserState().getAllUsers();
        if ((orgName == null) ||
                (orgAddress == null) ||
                (paymentAccountEmail == null) ||
                (mainRepName == null) ||
                (password == null) ||
                (otherRepNames == null) ||
                (otherRepEmails == null)) {
            entertainmentProvider = null;
        }
        if (allUsers.containsKey(mainRepEmail)) {
            entertainmentProvider = null;
        }
        for (User user: allUsers.values()) {
            if (user instanceof EntertainmentProvider) {
                if (((EntertainmentProvider) user).getOrgName().equals(orgName) ||
                        ((EntertainmentProvider) user).getOrgAddress().equals(orgAddress)) {
                            entertainmentProvider = null;
                }
            }
        }
    };

    @Override
    public EntertainmentProvider getResult() {
        return entertainmentProvider;
    };

}