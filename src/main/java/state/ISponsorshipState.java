package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

public interface ISponsorshipState {

    SponsorshipRequest addSponsorshipRequest(TicketedEvent event);

    SponsorshipRequest getAllSponsorshipRequests();

    SponsorshipRequest getPendingSponsorshipRequests();

    SponsorshipRequest findRequestByNumber(long requestNumber);

}