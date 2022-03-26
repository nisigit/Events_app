package model;

public class ConsumerPreferences extends Object {

    public boolean preferSocialDistancing() {
        return false;
    };

    public boolean preferAirFiltration() {
        return false;
    };

    public boolean preferOutdoorsOnly() {
        return false;
    };

    public int preferredMaxCapacity() {
        return Integer.MAX_VALUE;
    };

    public int preferredMaxVenueSize() {
        return Integer.MAX_VALUE;
    };

    ConsumerPreferences() {

    };

}