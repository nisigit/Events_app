package model;

import external.EntertainmentProviderSystem;
import external.MockEntertainmentProviderSystem;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

public class EntertainmentProvider extends User {

    private String orgName, orgAddress, mainRepName;
    private List<String> otherRepNames, otherRepEmails;
    private ArrayList<Event> events;
    private EntertainmentProviderSystem system;

    public EntertainmentProvider(String orgName, String orgAddress, String paymentAccountEmail,
                                 String mainRepName, String mainRepEmail, String password, List<String> otherRepNames,
                                 List<String> otherRepEmails) {
        super(mainRepEmail, password, paymentAccountEmail);
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.mainRepName = mainRepName;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
        this.events = new ArrayList<>();
        this.system = new MockEntertainmentProviderSystem(orgName, orgAddress);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setMainRepName(String mainRepName) {
        this.mainRepName = mainRepName;
    }

    public void setMainRepEmail(String mainRepEmail) {
        super.setEmail(mainRepEmail);
    }

    public void setOtherRepNames(List<String> otherRepNames) {
        this.otherRepNames = otherRepNames;
    }

    public void setOtherRepEmails(List<String> otherRepEmails) {
        this.otherRepEmails = otherRepEmails;
    }

    public EntertainmentProviderSystem getProviderSystem() {
        return this.system;
    }

    @Override
    public String toString() {
        return super.toString() + "EntertainmentProvider{" +
                "orgName='" + orgName + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                ", mainRepName='" + mainRepName + '\'' +
                ", otherRepNames=" + otherRepNames +
                ", otherRepEmails=" + otherRepEmails +
                ", events=" + events +
                ", system=" + system +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntertainmentProvider that = (EntertainmentProvider) o;

        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (orgAddress != null ? !orgAddress.equals(that.orgAddress) : that.orgAddress != null) return false;
        if (mainRepName != null ? !mainRepName.equals(that.mainRepName) : that.mainRepName != null) return false;
        if (otherRepNames != null ? !otherRepNames.equals(that.otherRepNames) : that.otherRepNames != null)
            return false;
        if (otherRepEmails != null ? !otherRepEmails.equals(that.otherRepEmails) : that.otherRepEmails != null)
            return false;
        if (events != null ? !events.equals(that.events) : that.events != null) return false;
        return system != null ? system.equals(that.system) : that.system == null;
    }

    @Override
    public int hashCode() {
        int result = orgName != null ? orgName.hashCode() : 0;
        result = 31 * result + (orgAddress != null ? orgAddress.hashCode() : 0);
        result = 31 * result + (mainRepName != null ? mainRepName.hashCode() : 0);
        result = 31 * result + (otherRepNames != null ? otherRepNames.hashCode() : 0);
        result = 31 * result + (otherRepEmails != null ? otherRepEmails.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        return result;
    }
}