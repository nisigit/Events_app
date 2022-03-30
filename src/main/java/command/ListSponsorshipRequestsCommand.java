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
        if (currentUser == null) return;
        if (!(currentUser instanceof GovernmentRepresentative)) return;
        else {
            result = context.getSponsorshipState().getAllSponsorshipRequests();
        };
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return result;
    }

}