package model;

public class ConsumerPreferences {

    /* the way the javadoc specifies it, there would be no possible way to change the preference attributes
    We've changed the implementation entirely; the attributes are assigned the default values if the constructor
    is called with no arguments, and are set to the desired values if the constructor DOES have arguments
    Additionally, we implement individual setters for each attribute
     */

    public boolean socialDistancing, airFiltration, outdoorsOnly;
    public int maxCapacity, maxVenueSize;

    public ConsumerPreferences() {
        this.socialDistancing = false;
        this.airFiltration = false;
        this.outdoorsOnly = false;
        this.maxCapacity = Integer.MAX_VALUE;
        this.maxVenueSize = Integer.MAX_VALUE;
    }

    public boolean preferSocialDistancing() {
        return socialDistancing;
    }

    public boolean preferAirFiltration() {
        return airFiltration;
    }

    public boolean preferOutdoorsOnly() {
        return outdoorsOnly;
    }

    public int preferredMaxCapacity() {
        return maxCapacity;
    }

    public int preferredMaxVenueSize() {
        return maxVenueSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsumerPreferences that = (ConsumerPreferences) o;

        if (socialDistancing != that.socialDistancing) return false;
        if (airFiltration != that.airFiltration) return false;
        if (outdoorsOnly != that.outdoorsOnly) return false;
        if (maxCapacity != that.maxCapacity) return false;
        return maxVenueSize == that.maxVenueSize;
    }

    @Override
    public int hashCode() {
        int result = (socialDistancing ? 1 : 0);
        result = 31 * result + (airFiltration ? 1 : 0);
        result = 31 * result + (outdoorsOnly ? 1 : 0);
        result = 31 * result + maxCapacity;
        result = 31 * result + maxVenueSize;
        return result;
    }
}