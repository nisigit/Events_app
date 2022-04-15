package command;

import controller.Context;
import external.PaymentSystem;
import logging.Logger;
import model.*;

public class RespondSponsorshipCommand implements ICommand {

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

        // Check Conditions
        if (user == null) return;
        if (request == null) return;
        if (percentToSponsor < 0 || percentToSponsor > 100) return;
        if (!(request.getStatus().equals(SponsorshipStatus.PENDING))) return;

        // If all tests passed, execute approve or reject the sponsorship
        if (user instanceof GovernmentRepresentative) {
            // If percent is 0, then reject
            if (percentToSponsor == 0) {
                request.reject();
                request.getEvent().getOrganiser().getProviderSystem().recordSponsorshipRejection(request.getEvent().getEventNumber());
            }
            // if there's amount, then approve it
            else {
                request.accept(percentToSponsor, user.getPaymentAccountEmail());
                TicketedEvent event = request.getEvent();
                // Gather emails for payment
                String sellerEmail = event.getOrganiser().getEmail();
                String governmentEmail = event.getSponsorAccountEmail();
                // Calculate the amount of sponsorship
                double originalPrice = event.getOriginalTicketPrice();
                int numTickets = event.getNumTickets();
                double sponsorshipAmount = originalPrice * (0.01 * percentToSponsor) * numTickets;
                paymentSystem.processPayment(governmentEmail, sellerEmail, sponsorshipAmount);
                request.getEvent().getOrganiser().getProviderSystem().recordSponsorshipAcceptance(
                        request.getEvent().getEventNumber(), percentToSponsor);
            }
            this.successResult = true;
        }

        Logger.getInstance().logAction("RespondSponsorshipCommand", successResult);
    }

    @Override
    public Boolean getResult() {
        return this.successResult;
    }

}