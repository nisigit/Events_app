package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.*;

import java.util.List;
import java.util.Map;

public class RegisterEntertainmentProviderCommand implements ICommand {
    enum LogStatus{
        REGISTER_ENTERTAINMENT_PROVIDER_SUCCESS,
        USER_REGISTER_FIELDS_CANNOT_BE_NULL,
        USER_REGISTER_EMAIL_ALREADY_REGISTERED,
        USER_REGISTER_ORG_ALREADY_REGISTERED,
        USER_LOGIN_SUCCESS,
    }

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
        Logger logger = Logger.getInstance();
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
            logger.logAction("RegisterEntertainmentProviderCommand", LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);
            return;
        }
        if (allUsers.containsKey(mainRepEmail)) {
            logger.logAction("RegisterEntertainmentProviderCommand", LogStatus.USER_REGISTER_EMAIL_ALREADY_REGISTERED);
            newEntertainmentProviderResult = null;
            return;
        }
        for (User user: allUsers.values()) {
            if (user instanceof EntertainmentProvider) {
                if (((EntertainmentProvider) user).getOrgName().equals(orgName) ||
                        ((EntertainmentProvider) user).getOrgAddress().equals(orgAddress)) {
                    logger.logAction("RegisterEntertainmentProviderCommand", LogStatus.USER_REGISTER_ORG_ALREADY_REGISTERED);
                    newEntertainmentProviderResult = null;
                    return;
                }
            }
        }
        // After passing the checking, add the registered entertainment provider and log it in
        if (newEntertainmentProviderResult != null) {
            context.getUserState().addUser(newEntertainmentProviderResult);
            logger.logAction("RegisterEntertainmentProviderCommand", LogStatus.REGISTER_ENTERTAINMENT_PROVIDER_SUCCESS);
            context.getUserState().setCurrentUser(newEntertainmentProviderResult);
            logger.logAction("RegisterEntertainmentProviderCommand", LogStatus.USER_LOGIN_SUCCESS);
        }
    }

    @Override
    public EntertainmentProvider getResult() {
        return newEntertainmentProviderResult;
    }

}