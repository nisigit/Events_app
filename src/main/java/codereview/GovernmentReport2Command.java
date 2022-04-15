package codereview;

import command.ICommand;
import controller.Context;
import logging.Logger;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class GovernmentReport2Command implements ICommand {

    enum LogStatus {
        GOVERNMENT_REPORT2_NOT_LOGGED_IN,
        GOVERNMENT_REPORT2_USER_NOT_GOVERNMENT_REPRESENTATIVE,
        GOVERNMENT_REPORT2_NO_SUCH_ORGANISATION,
        GOVERNMENT_REPORT2_SUCCESS,
    }

    private final String orgName;
    private List<Consumer> reportConsumers;

    public GovernmentReport2Command(String orgName) {
        this.orgName = orgName;
        this.reportConsumers = null;
    }

    @Override
    public void execute(Context context) {
        User loggedInUser = context.getUserState().getCurrentUser();
        assert loggedInUser != null : LogStatus.GOVERNMENT_REPORT2_NOT_LOGGED_IN;
        assert loggedInUser instanceof GovernmentRepresentative : LogStatus.GOVERNMENT_REPORT2_USER_NOT_GOVERNMENT_REPRESENTATIVE;
        boolean validOrgName = false;
        List<Consumer> consumers = new ArrayList<>();

        // get active bookings for active ticketed events of particular organisation
        for (Event event : context.getEventState().getAllEvents()) {
            if (event.getOrganiser().getOrgName().equals(orgName)) {
                validOrgName = true;
                if (event instanceof TicketedEvent && event.getStatus().equals(EventStatus.ACTIVE)) {
                    List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(event.getEventNumber());
                    for (Booking booking : bookings) {
                        if (booking.getStatus().equals(BookingStatus.Active)) {
                            Consumer booker = booking.getBooker();
                            if (!consumers.contains(booker)) {
                                consumers.add(booker);
                            }
                        }
                    }
                }
            }
        }
        assert validOrgName : LogStatus.GOVERNMENT_REPORT2_NO_SUCH_ORGANISATION;
        this.reportConsumers = consumers;
        Logger.getInstance().logAction("GovernmentReportCommand", LogStatus.GOVERNMENT_REPORT2_SUCCESS);

    }

    @Override
    public List<Consumer> getResult() {
        return this.reportConsumers;
    }
}
