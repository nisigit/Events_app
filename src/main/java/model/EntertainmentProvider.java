package model;

import external.EntertainmentProviderSystem;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class EntertainmentProvider extends User {

    private String orgName, orgAddress, mainRepName;
    private List<String> otherRepNames, otherRepEmails;
    private ArrayList<Event> events;
    public EntertainmentProvider(String orgName, String orgAddress, String paymentAccountEmail,
                                 String mainRepName, String mainRepEmail, String password, List<String> otherRepNames,
                                 List<String> otherRepEmails) throws InvalidKeySpecException, NoSuchAlgorithmException {
        super(mainRepEmail, password, paymentAccountEmail);
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.mainRepName = mainRepName;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
        this.events = new ArrayList<>();
    };

    public void addEvent(Event event) {
        this.events.add(event);
    };

    public String getOrgName() {
        return orgName;
    };

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    };

    public String getOrgAddress() {
        return orgAddress;
    };

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    };

    public List<Event> getEvents() {
        return events;
    };

    public void setMainRepName(String mainRepName) {
        
    };

    public void setMainRepEmail(String mainRepEmail) {

    };

    public void setOtherRepNames(List<String> otherRepNames) {

    };

    public void setOtherRepEmails(List<String> otherRepEmails) {

    };

    public EntertainmentProviderSystem getProviderSystem() {

    };

    @Override
    public String toString() {

    };

}