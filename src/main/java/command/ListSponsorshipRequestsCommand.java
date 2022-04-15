package command;

import controller.Context;
import logging.Logger;
import model.SponsorshipRequest;
import model.*;

import java.util.*;

public class ListSponsorshipRequestsCommand implements ICommand {

    private boolean pendingRequestsOnly;
    private List<SponsorshipRequest> requestListResult;

    public ListSponsorshipRequestsCommand(boolean pendingRequestsOnly) {
        this.pendingRequestsOnly = pendingRequestsOnly;
        this.requestListResult = new ArrayList<>();
    }

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();
        List<SponsorshipRequest> allSponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();
        // Condition checks
        if (currentUser == null) return;
        if (!(currentUser instanceof GovernmentRepresentative)) return;

        // Filter the sponsorship requests and add them to the output list
        for (SponsorshipRequest sr: allSponsorshipRequests) {
            if (sr.getStatus() == SponsorshipStatus.PENDING) {
                requestListResult.add(sr);
            }
        }

        Logger.getInstance().logAction("ListSponsorshipRequestsCommand", requestListResult);
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return requestListResult;
    }

}