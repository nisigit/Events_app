package command;

import controller.Context;
import external.PaymentSystem;
import logging.Logger;
import model.*;

public class RespondSponsorshipCommand implements ICommand {

    enum LogStatus {
        RESPOND_SPONSORSHIP_APPROVE,
        RESPOND_SPONSORSHIP_REJECT,
        RESPOND_SPONSORSHIP_USER_NOT_LOGGED_IN,
        RESPOND_SPONSORSHIP_USER_NOT_GOVERNMENT_REPRESENTATIVE,
        RESPOND_SPONSORSHIP_REQUEST_NOT_FOUND,
        RESPOND_SPONSORSHIP_INVALID_PERCENTAGE,
        RESPOND_SPONSORSHIP_REQUEST_NOT_PENDING,
        RESPOND_SPONSORSHIP_PAYMENT_SUCCESS,
        RESPOND_SPONSORSHIP_PAYMENT_FAILED
    }


    private long requestNumber;
    private int percentToSponsor;
    private boolean successResult;

    public RespondSponsorshipCommand(long requestNumber, int percentToSponsor) {
        this.requestNumber = requestNumber;
        this.percentToSponsor = percentToSponsor;
    }

    @Override
    public void execute(Context context) {
        this.successResult = false;
        User user = context.getUserState().getCurrentUser();
        SponsorshipRequest request = context.getSponsorshipState().findRequestByNumber(this.requestNumber);
        PaymentSystem paymentSystem = context.getPaymentSystem();
        Logger logger = Logger.getInstance();

        // Check Conditions
        if (user == null) {
            logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_USER_NOT_LOGGED_IN);
            return;
        }
        if (request == null) {
            logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_REQUEST_NOT_FOUND);
            return;
        }
        if (percentToSponsor < 0 || percentToSponsor > 100) {
            logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_INVALID_PERCENTAGE);
            return;
        }
        if (!(request.getStatus().equals(SponsorshipStatus.PENDING))) {
            logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_REQUEST_NOT_PENDING);
            return;
        }

        // If all tests passed, execute approve or reject the sponsorship
        if (user instanceof GovernmentRepresentative) {
            // If percent is 0, then reject
            if (percentToSponsor == 0) {
                request.reject();
                request.getEvent().getOrganiser().getProviderSystem().recordSponsorshipRejection(request.getEvent().getEventNumber());
                logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_REJECT);
            }
            // if there's amount, then approve it
            else {
                logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_APPROVE);

                TicketedEvent event = request.getEvent();
                // Gather emails for payment
                String sellerEmail = event.getOrganiser().getEmail();
                String governmentEmail = user.getEmail();

                // Calculate the amount of sponsorship
                double originalPrice = event.getOriginalTicketPrice();
                int numTickets = event.getNumTickets();
                double sponsorshipAmount = originalPrice * (0.01 * percentToSponsor) * numTickets;

                // check if payment works
                if (paymentSystem.processPayment(governmentEmail, sellerEmail, sponsorshipAmount)) {
                    request.accept(percentToSponsor, user.getPaymentAccountEmail());
                    request.getEvent().getOrganiser().getProviderSystem().recordSponsorshipAcceptance(
                            request.getEvent().getEventNumber(), percentToSponsor);
                    logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_PAYMENT_SUCCESS);
                }
                else logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_PAYMENT_FAILED);
            }
            this.successResult = true;
        }
        else {
            logger.logAction("RespondSponsorshipCommand", LogStatus.RESPOND_SPONSORSHIP_USER_NOT_GOVERNMENT_REPRESENTATIVE);
        }
    }

    @Override
    public Boolean getResult() {
        return this.successResult;
    }

}