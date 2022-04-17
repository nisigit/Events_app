package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.User;

import java.util.List;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand {
    enum LogStatus {
        USER_UPDATE_PROFILE_SUCCESS,
        USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL,
        USER_UPDATE_PROFILE_NOT_ENTERTAINMENT_PROVIDER,
        USER_UPDATE_PROFILE_ORG_ALREADY_REGISTERED
    }
    private String oldPassword, newOrgName, newOrgAddress, newPaymentAccountEmail,
            newMainRepName, newMainRepEmail, newPassword;
    private List<String> newOtherRepNames, newOtherRepEmails;

    public UpdateEntertainmentProviderProfileCommand(String oldPassword, String newOrgName, String newOrgAddress,
                                                     String newPaymentAccountEmail, String newMainRepName,
                                                     String newMainRepEmail, String newPassword,
                                                     List<String> newOtherRepNames, List<String> newOtherRepEmails) {
        // Initialization of fields
        this.oldPassword = oldPassword;
        this.newOrgName = newOrgName;
        this.newOrgAddress = newOrgAddress;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newMainRepName = newMainRepName;
        this.newMainRepEmail = newMainRepEmail;
        this.newPassword = newPassword;
        this.newOtherRepNames = newOtherRepNames;
        this.newOtherRepEmails = newOtherRepEmails;
    }

    public void execute(Context context) {
        this.successResult = false;
        Logger logger = Logger.getInstance();

        User user = context.getUserState().getCurrentUser();
        // Condition checks
        boolean isNull = oldPassword == null && newOrgName == null && newOrgAddress == null
                && newPaymentAccountEmail == null && newMainRepName == null && newMainRepEmail == null
                && newPassword == null && newOtherRepNames == null && newOtherRepEmails == null;

        // Using assertions to check conditions

//        assert (isNull): "The update profile fields cannot be null";
//        assert (isProfileUpdateInvalid(context, oldPassword, newMainRepEmail)): "Invalid request";
//        for (User i: context.getUserState().getAllUsers().values()) {
//            if (i instanceof EntertainmentProvider) {
//                assert  (((EntertainmentProvider) i).getOrgName().equals(newOrgName) ||
//                        ((EntertainmentProvider) i).getOrgAddress().equals(newOrgAddress)): "Updated profile already registered";
//            }
//        }

        if (isNull) {
            logger.logAction("UpdateEntertainmentProviderProfileCommand", LogStatus.USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL);
            return;
        }

        if (isProfileUpdateInvalid(context, oldPassword, newMainRepEmail)) return;

        // check if organisation details don't already exist in our database
        for (User i: context.getUserState().getAllUsers().values()) {
            if (i instanceof EntertainmentProvider) {
                if (((EntertainmentProvider) i).getOrgName().equals(newOrgName) ||
                        ((EntertainmentProvider) i).getOrgAddress().equals(newOrgAddress)) {
                    logger.logAction("UpdateEntertainmentProviderProfileCommand", LogStatus.USER_UPDATE_PROFILE_ORG_ALREADY_REGISTERED);
                    return;
                }
            }
        }

        // If all conditions passed, then update the profile accordingly
        if (user instanceof EntertainmentProvider) {
            EntertainmentProvider provider = (EntertainmentProvider) user;
            provider.setOrgName(newOrgName);
            provider.setOrgAddress(newOrgAddress);
            provider.setPaymentAccountEmail(newPaymentAccountEmail);
            provider.setMainRepName(newMainRepName);
            provider.setMainRepEmail(newMainRepEmail);
            provider.updatePassword(newPassword);
            provider.setOtherRepNames(newOtherRepNames);
            provider.setOtherRepEmails(newOtherRepEmails);
            logger.logAction("UpdateEntertainmentProviderProfileCommand", LogStatus.USER_UPDATE_PROFILE_SUCCESS);

            this.successResult = true;
        }
        else logger.logAction("UpdateEntertainmentProviderProfileCommand", LogStatus.USER_UPDATE_PROFILE_NOT_ENTERTAINMENT_PROVIDER);
    }

}