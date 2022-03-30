package command;

import controller.Context;
import model.EntertainmentProvider;
import model.User;

import java.util.List;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand {

    private String oldPassword, newOrgName, newOrgAddress, newPaymentAccountEmail,
            newMainRepName, newMainRepEmail, newPassword;
    private List<String> newOtherRepNames, newOtherRepEmails;

    public UpdateEntertainmentProviderProfileCommand(String oldPassword, String newOrgName, String newOrgAddress,
                                                     String newPaymentAccountEmail, String newMainRepName,
                                                     String newMainRepEmail, String newPassword,
                                                     List<String> newOtherRepNames, List<String> newOtherRepEmails) {
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

        User user = context.getUserState().getCurrentUser();
        boolean isNull = oldPassword == null && newOrgName == null && newOrgAddress == null
                && newPaymentAccountEmail == null && newMainRepName == null && newMainRepEmail == null
                && newPassword == null && newOtherRepNames == null && newOtherRepEmails == null && user == null;
        if (isNull) return;

        if (isProfileUpdateInvalid(context, oldPassword, newMainRepEmail)) return;

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

            this.successResult = true;
        }

    }

}