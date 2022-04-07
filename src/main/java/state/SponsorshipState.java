package state;

import model.SponsorshipRequest;
import model.SponsorshipStatus;
import model.TicketedEvent;

import java.util.ArrayList;
import java.util.List;

public class SponsorshipState implements ISponsorshipState {

    private long nextRequestNumber = 0;
    private ArrayList<SponsorshipRequest> sponsorshipRequests;

    public SponsorshipState() {
        this.nextRequestNumber++;
        this.sponsorshipRequests = new ArrayList<>();
    }

    public SponsorshipState(ISponsorshipState other) {
        SponsorshipState x = (SponsorshipState) other;
        this.nextRequestNumber = x.getNextRequestNumber();
        this.sponsorshipRequests = (ArrayList<SponsorshipRequest>) x.getAllSponsorshipRequests();
    }

    @Override
    public SponsorshipRequest addSponsorshipRequest(TicketedEvent event) {
        // Create a new Request object and add it to the list
        SponsorshipRequest request = new SponsorshipRequest(this.nextRequestNumber, event);
        this.nextRequestNumber++;
        this.sponsorshipRequests.add(request);
        return request;
    }

    @Override
    public List<SponsorshipRequest> getAllSponsorshipRequests() {
        return sponsorshipRequests;
    }


    @Override
    public List<SponsorshipRequest> getPendingSponsorshipRequests() {
        ArrayList<SponsorshipRequest> temp = new ArrayList<>();
        for (SponsorshipRequest i : sponsorshipRequests) {
            if (i.getStatus() == SponsorshipStatus.PENDING) {
                temp.add(i);
            }
        }
        return temp;
    }

    @Override
    public SponsorshipRequest findRequestByNumber(long requestNumber) {
        for (SponsorshipRequest i : sponsorshipRequests) {
            if (i.getRequestNumber() == requestNumber) {
                return i;
            }
        }
        return null;
    }

    public long getNextRequestNumber() {
        return nextRequestNumber;
    }
}