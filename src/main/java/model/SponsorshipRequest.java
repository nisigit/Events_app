package model;

public class SponsorshipRequest {

    private long requestNumber;
    private TicketedEvent event;
    private SponsorshipStatus status;
    private int percent;
    private String sponsorAccountEmail;

    public SponsorshipRequest(long requestNumber, TicketedEvent event) {
        this.requestNumber = requestNumber;
        this.event = event;
        this.status = SponsorshipStatus.PENDING;
    }

    public long getRequestNumber() {
        return requestNumber;
    }

    public TicketedEvent getEvent() {
        return event;
    }

    public SponsorshipStatus getStatus() {
        return status;
    }

    public Integer getSponsoredPricePercent() {
        if (getStatus() == SponsorshipStatus.ACCEPTED) {
            return percent;
        } else {
            return null;
        }
    }

    public String getSponsorAccountEmail() {
        if (status == SponsorshipStatus.ACCEPTED) {
            return sponsorAccountEmail;
        }
        return null;
    }

    public void accept(int percent, String sponsorAccountEmail) {
        status = SponsorshipStatus.ACCEPTED;
        this.percent = percent;
        this.sponsorAccountEmail = sponsorAccountEmail;
    }

    public void reject() {
        status = SponsorshipStatus.REJECTED;
    }

}