package model;

public class NonTicketedEvent extends Event {

    private final long eventNumber;
    private final EntertainmentProvider organiser;
    private final String title;
    private final EventType type;


    public NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        super();
        this.eventNumber = eventNumber;
        this.organiser = organiser;
        this.title = title;
        this.type = type;
    };

    @Override
    public String toString() {
        return "NonTicketedEvent{" +
                "eventNumber=" + eventNumber +
                ", organiser=" + organiser +
                ", title='" + title + '\'' +
                ", type=" + type +
                '}';
    }
}
