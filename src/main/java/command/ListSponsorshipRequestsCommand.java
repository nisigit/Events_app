package command;

import controller.Context;
import logging.Logger;
import model.SponsorshipRequest;
import model.*;

import java.util.*;

public class ListSponsorshipRequestsCommand implements ICommand {
    enum LogStatus {
        LIST_SPONSORSHIP_REQUESTS_NOT_LOGGED_IN,
        LIST_SPONSORSHIP_REQUESTS_NOT_GOVERNMENT_REPRESENTATIVE,
        LIST_SPONSORSHIP_REQUESTS_SUCCESS
    }
    private boolean pendingRequestsOnly;
    private List<SponsorshipRequest> requestListResult;

    public ListSponsorshipRequestsCommand(boolean pendingRequestsOnly) {
        this.pendingRequestsOnly = pendingRequestsOnly;
        this.requestListResult = new ArrayList<>();
    }

    @Override
    public void execute(Context context) {
        Logger logger = Logger.getInstance();
        User currentUser = context.getUserState().getCurrentUser();
        List<SponsorshipRequest> allSponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();
        // Condition checks
        if (currentUser == null) {
            logger.logAction("ListSponsorshipRequestsCommand", LogStatus.LIST_SPONSORSHIP_REQUESTS_NOT_LOGGED_IN);
            return;
        }
        if (!(currentUser instanceof GovernmentRepresentative)) {
            logger.logAction("ListSponsorshipRequestsCommand", LogStatus.LIST_SPONSORSHIP_REQUESTS_NOT_GOVERNMENT_REPRESENTATIVE);
            return;
        }

        // Filter the sponsorship requests and add them to the output list
        if (pendingRequestsOnly) {
            for (SponsorshipRequest sr: allSponsorshipRequests) {
                if (sr.getStatus() == SponsorshipStatus.PENDING) {
                    requestListResult.add(sr);
                }
            }
        }
        else {
            requestListResult = new ArrayList<>(allSponsorshipRequests);
        }
        logger.logAction("ListSponsorshipRequestsCommand", LogStatus.LIST_SPONSORSHIP_REQUESTS_SUCCESS);
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return requestListResult;
    }

}