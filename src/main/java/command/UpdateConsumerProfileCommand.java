package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.ConsumerPreferences;
import model.User;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand {
    enum LogStatus {
        USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL,
        USER_UPDATE_PROFILE_NOT_CONSUMER,
        USER_UPDATE_PROFILE_SUCCESS
    }

    private String oldPassword, newName, newEmail, newPhoneNumber, newPassword, newPaymentAccountEmail;
    private ConsumerPreferences newPreferences;

    public UpdateConsumerProfileCommand(String oldPassword, String newName, String newEmail,
                                        String newPhoneNumber, String newPassword, String newPaymentAccountEmail,
                                        ConsumerPreferences newPreferences) {
        // Initializing the fields
        this.oldPassword = oldPassword;
        this.newName = newName;
        this.newEmail = newEmail;
        this.newPhoneNumber = newPhoneNumber;
        this.newPassword = newPassword;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newPreferences = newPreferences;
    }

    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        this.successResult = false;

        User user = context.getUserState().getCurrentUser();
        // Condition checks
        boolean isNull = oldPassword == null && newName == null && newEmail == null && newPhoneNumber == null &&
                newPassword == null && newPaymentAccountEmail == null && newPreferences == null;
        if (isNull) {
            logger.logAction("UpdateConsumerProfileCommand", LogStatus.USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL);
            return;
        }

        if (isProfileUpdateInvalid(context, oldPassword, newEmail)) return;

        //if all conditions passed, then update the consumer information
        if (user instanceof Consumer) {
            Consumer consumer = (Consumer) user;
            consumer.setName(newName);
            consumer.setEmail(newEmail);
            consumer.setPhoneNumber(newPhoneNumber);
            consumer.updatePassword(newPassword);
            consumer.setPaymentAccountEmail(newPaymentAccountEmail);
            consumer.setPreferences(newPreferences);
            logger.logAction("UpdateConsumerProfileCommand", LogStatus.USER_UPDATE_PROFILE_SUCCESS);

            this.successResult = true;
        }
        else logger.logAction("UpdateConsumerProfileCommand", LogStatus.USER_UPDATE_PROFILE_NOT_CONSUMER);
    }

}