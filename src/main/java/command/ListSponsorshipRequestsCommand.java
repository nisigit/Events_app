package command;

import controller.Context;
import model.SponsorshipRequest;
import model.*;

import java.util.*;

public class ListSponsorshipRequestsCommand implements ICommand {

    private boolean pendingRequestsOnly;
    private List<SponsorshipRequest> result;

    public ListSponsorshipRequestsCommand(boolean pendingRequestsOnly) {
        this.pendingRequestsOnly = pendingRequestsOnly;
    }

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();
        List<SponsorshipRequest> allSponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();
        if (currentUser == null) return;
        if (!(currentUser instanceof GovernmentRepresentative)) return;
        else {
            for (SponsorshipRequest sr: allSponsorshipRequests) {
                if (sr.getStatus() == SponsorshipStatus.PENDING) {
                    result.add(sr);
                }
            }
        };
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return result;
    }

}