package model;

public class TicketedEvent extends Event {

    private double ticketPrice;
    private int numTickets;
    private SponsorshipRequest sponsorshipRequest;

    public TicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets) {
        super(eventNumber, organiser, title, type);
        this.ticketPrice = ticketPrice;
        this.numTickets = numTickets;
    }

    public double getOriginalTicketPrice() {
        return ticketPrice;
    }

    public double getDiscountedTicketPrice() {
        if (isSponsored()) {
            int sponsorPercent = sponsorshipRequest.getSponsoredPricePercent();
            return ticketPrice * (100 - sponsorPercent) / 100;
        }
        else {
            return ticketPrice;
        }
    }

    @Override
    public void addPerformance(EventPerformance performance) {
        super.addPerformance(performance);
        getOrganiser().getProviderSystem().recordNewPerformance(this.eventNumber, performance.getPerformanceNumber(),
                performance.getStartDateTime(), performance.getEndDateTime());
    }

    public int getNumTickets() {
        return numTickets;
    }

    public String getSponsorAccountEmail() {
        return sponsorshipRequest.getSponsorAccountEmail();
    }

    public boolean isSponsored() {
        return sponsorshipRequest.getStatus() == SponsorshipStatus.ACCEPTED;
    }

    public void setSponsorshipRequest(SponsorshipRequest sponsorshipRequest) {
        this.sponsorshipRequest = sponsorshipRequest;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTicket price: " + ticketPrice +
                "\nNumber of tickets left: " + getOrganiser().getProviderSystem().getNumTicketsLeft(eventNumber, -1)
                +"\nSponsorship request" + sponsorshipRequest;

//        return "TicketedEvent{" +
//                "ticketPrice=" + ticketPrice +
//                ", numTickets=" + getOrganiser().getProviderSystem().getNumTicketsLeft(eventNumber, -1) +
//                ", sponsorshipRequest=" + sponsorshipRequest +
//                '}';
    }
}
