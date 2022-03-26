package model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class Consumer extends User {

    private ArrayList<Booking> bookings;
    private String name, phoneNumber;
    private ConsumerPreferences prefs;

    public Consumer(String name, String email, String phoneNumber, String password, String paymentAccountEmail)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        super(email, password, paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.prefs = new ConsumerPreferences();
    };

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    };

    public String getName() {
        return this.name;
    };

    public ConsumerPreferences getPreferences() {
        return this.prefs;
    };

    public void setPreferences(ConsumerPreferences preferences) {
        this.prefs = preferences;
    };

    public List<Booking> getBookings() {
        return bookings;
    };

    public void notify(String message) {

    };

    public void setName(String newName) {
        this.name = newName;
    };

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    };

    public String toString() {
        return super.toString() +
                "Consumer{" +
                "bookings=" + bookings +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", prefs=" + prefs +
                '}';
    }
}