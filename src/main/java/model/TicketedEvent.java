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

    };

    public int getNumTickets() {

    };

    public String getSponsorAccountEmail() {

    };

    public boolean isSponsored() {

    };

    public void setSponsorshipRequest(SponsorshipRequest sponsorshipRequest) {

    };

    public String toString() {

    };

}
