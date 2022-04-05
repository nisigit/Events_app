package command;

import controller.Context;
import model.GovernmentRepresentative;
import model.SponsorshipRequest;
import model.SponsorshipStatus;
import model.User;

public class RespondSponsorshipCommand implements ICommand {

    private long requestNumber;
    private int percentToSponsor;
    private boolean result;

    public RespondSponsorshipCommand(long requestNumber, int percentToSponsor) {
        this.requestNumber = requestNumber;
        this.percentToSponsor = percentToSponsor;
    }

    @Override
    public void execute(Context context) {
        this.result = false;
        User user = context.getUserState().getCurrentUser();
        SponsorshipRequest request = context.getSponsorshipState().findRequestByNumber(this.requestNumber);

        // Check Conditions
        if (user == null) return;
        if (request == null) return;
        if (percentToSponsor < 0 || percentToSponsor > 100) return;
        if (!(request.getStatus().equals(SponsorshipStatus.PENDING))) return;

        // TODO Still unsure if we should accept and reject based on amount
        // If all tests passed, execute approve or reject the sponsorship
        if (user instanceof GovernmentRepresentative) {
            // If percent is 0, then reject
            if (percentToSponsor == 0) {
                request.reject();
            }
            // if there's amount, then approve it
            else {
                request.accept(percentToSponsor, user.getPaymentAccountEmail());
            }
            this.result = true;
        }
    }

    @Override
    public Boolean getResult() {
        return this.result;
    }

}