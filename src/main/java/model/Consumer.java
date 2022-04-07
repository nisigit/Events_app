package model;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends User {


    private String name, phoneNumber;
    private ArrayList<Booking> bookings;
    private ConsumerPreferences prefs;

    public Consumer(String name, String email, String phoneNumber, String password, String paymentAccountEmail) {
        super(email, password, paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.prefs = new ConsumerPreferences();
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public String getName() {
        return this.name;
    }

    public ConsumerPreferences getPreferences() {
        return this.prefs;
    }

    public void setPreferences(ConsumerPreferences preferences) {
        this.prefs = preferences;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    //TODO Find usage of notify
    public void notify(String message) {
        System.out.println(message);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "Consumer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookings=" + bookings +
                ", prefs=" + prefs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Consumer consumer = (Consumer) o;

        if (name != null ? !name.equals(consumer.name) : consumer.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(consumer.phoneNumber) : consumer.phoneNumber != null)
            return false;
        if (bookings != null ? !bookings.equals(consumer.bookings) : consumer.bookings != null) return false;
        return prefs != null ? prefs.equals(consumer.prefs) : consumer.prefs == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (bookings != null ? bookings.hashCode() : 0);
        result = 31 * result + (prefs != null ? prefs.hashCode() : 0);
        return result;
    }
}