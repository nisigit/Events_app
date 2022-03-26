package model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class Consumer extends User {


    private String name, phoneNumber;
    private ArrayList<Booking> bookings;
    private ConsumerPreferences prefs;

    public Consumer(String name, String email, String phoneNumber, String password, String paymentAccountEmail)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        super(email, password, paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.prefs = new ConsumerPreferences();
        this.bookings = new ArrayList<>();
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

    };

    public void setPhoneNumber(String newPhoneNumber) {

    };

    @Override
    public String toString() {
        return super.toString() + "Consumer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bookings=" + bookings +
                ", prefs=" + prefs +
                '}';
    }
}