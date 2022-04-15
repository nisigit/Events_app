package model;

public class NonTicketedEvent extends Event {

    public NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        super(eventNumber, organiser, title, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void addPerformance(EventPerformance performance) {
        super.addPerformance(performance);
    }
}
