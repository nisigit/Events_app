package model;

public class NonTicketedEvent extends Event {

    public NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        super(eventNumber, organiser, title, type);
    }

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
