package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.*;

import java.util.List;
import java.util.Map;

public class RegisterEntertainmentProviderCommand implements ICommand {

    private String orgName, orgAddress, paymentAccountEmail, mainRepName, mainRepEmail, password;
    private List<String> otherRepNames;
    private List<String> otherRepEmails;
    private EntertainmentProvider newEntertainmentProviderResult;

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
        this.newEntertainmentProviderResult = entertainmentProvider;
    }

    @Override
    public void execute(Context context) {
        Map<String, User> allUsers = context.getUserState().getAllUsers();
        // Checking Conditions
        if ((orgName == null) ||
                (orgAddress == null) ||
                (paymentAccountEmail == null) ||
                (mainRepName == null) ||
                (password == null) ||
                (otherRepNames == null) ||
                (otherRepEmails == null)) {
            newEntertainmentProviderResult = null;
            return;
        }
        if (allUsers.containsKey(mainRepEmail)) {
            newEntertainmentProviderResult = null;
            return;
        }
        for (User user: allUsers.values()) {
            if (user instanceof EntertainmentProvider) {
                if (((EntertainmentProvider) user).getOrgName().equals(orgName) ||
                        ((EntertainmentProvider) user).getOrgAddress().equals(orgAddress)) {
                            newEntertainmentProviderResult = null;
                            return;
                }
            }
        }
        // After passing the checking, add the registered entertainment provider and log it in
        if (newEntertainmentProviderResult != null) {
            context.getUserState().setCurrentUser(newEntertainmentProviderResult);
            context.getUserState().addUser(newEntertainmentProviderResult);
        }

        Logger.getInstance().logAction("RegisterEntertainmentProviderCommand", newEntertainmentProviderResult);
    }

    @Override
    public EntertainmentProvider getResult() {
        return newEntertainmentProviderResult;
    }

}