package model;

public class TicketedEvent extends Event {

    private long eventNumber;
    private EntertainmentProvider organiser;
    private String title;
    private EventType type;
    private double ticketPrice;
    private int numTickets;

    public TicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets) {
        super(eventNumber, organiser, title, type);
        this.ticketPrice = ticketPrice;
        this.numTickets = numTickets;
    };

    public double getOriginalTicketPrice() {
        return ticketPrice;
    };

    public double getDiscountedTicketPrice() {
        boolean sponsored = isSponsored();
        if (sponsored) {

        }
        else {
            return ticketPrice;
        }
    };

    public int getNumTickets() {
        return numTickets;
    };

    public String getSponsorAccountEmail() {
        return ;
    };

    public boolean isSponsored() {
        return SponsorshipStatus
    };

    public void setSponsorshipRequest(SponsorshipRequest sponsorshipRequest) {

    };

    @Override
    public String toString() {
        return super.toString() +
                "ticketPrice=" + ticketPrice +
                ", numTickets=" + numTickets +
                '}';
    }
}
