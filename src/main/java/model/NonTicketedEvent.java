package model;

public class NonTicketedEvent extends Event {

    private long eventNumber;
    private EntertainmentProvider organiser;
    private String title;
    private EventType type;


    public NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        super(eventNumber, organiser, title, type);
    };

    @Override
    public String toString() {
        return "NonTicketedEvent{" +
                "eventNumber=" + this.getEventNumber() +
                ", organiser=" + this.getOrganiser() +
                ", title='" + this.getTitle() + '\'' +
                ", type=" + this.getType() +
                '}';
    }
}
