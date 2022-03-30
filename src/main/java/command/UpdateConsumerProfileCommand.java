package command;

import controller.Context;
import model.Consumer;
import model.ConsumerPreferences;
import model.User;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand {

    private String oldPassword, newName, newEmail, newPhoneNumber, newPassword, newPaymentAccountEmail;
    private ConsumerPreferences newPreferences;

    public UpdateConsumerProfileCommand(String oldPassword, String newName, String newEmail,
                                        String newPhoneNumber, String newPassword, String newPaymentAccountEmail,
                                        ConsumerPreferences newPreferences) {
        this.oldPassword = oldPassword;
        this.newName = newName;
        this.newEmail = newEmail;
        this.newPhoneNumber = newPhoneNumber;
        this.newPassword = newPassword;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newPreferences = newPreferences;
    }

    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        boolean isNull = oldPassword == null && newName == null && newEmail == null && newPhoneNumber == null &&
                newPassword == null && newPaymentAccountEmail == null && newPreferences == null && user == null;
        if (isNull) return;

        if (isProfileUpdateInvalid(context, oldPassword, newEmail)) return;

        if (user instanceof Consumer) {
            Consumer consumer = (Consumer) user;
            consumer.setName(newName);
            consumer.setEmail(newEmail);
            consumer.setPhoneNumber(newPhoneNumber);
            consumer.updatePassword(newPassword);
            consumer.setPaymentAccountEmail(newPaymentAccountEmail);
            consumer.setPreferences(newPreferences);
        }
    }

}