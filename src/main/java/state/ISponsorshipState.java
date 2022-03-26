package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

import java.util.List;

public interface ISponsorshipState {

    SponsorshipRequest addSponsorshipRequest(TicketedEvent event);

    List<SponsorshipRequest> getAllSponsorshipRequests();

    List<SponsorshipRequest> getPendingSponsorshipRequests();

    SponsorshipRequest findRequestByNumber(long requestNumber);

}