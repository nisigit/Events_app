package tests;

import command.*;
import controller.Controller;
import model.EventType;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

public class CancelEventSystemTest {
    private void registerUsers(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Nitndeo",
                "Nippon",
                "doug.bowser@paypal.com",
                "Reggie Jr.",
                "regigigas@nitndeo.com",
                "sonicBae",
                new ArrayList<>(), new ArrayList<>()));
        controller.runCommand(new LogoutCommand());

        controller.runCommand(new RegisterConsumerCommand(
                "Not Aprovider",
                "n.provider@noevents.com",
                "69696969696",
                "eventsn't",
                "n.provider@noevents.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private Long createEvent1(Controller controller) {
        controller.runCommand(new LoginCommand("regigigas@nitndeo.com", "sonicBae"));
        CreateNonTicketedEventCommand ntEvent = new CreateNonTicketedEventCommand("BOTW 2 release party", EventType.Dance);
        controller.runCommand(ntEvent);
        controller.runCommand(new LogoutCommand());
        return ntEvent.getResult();
    }

    private Long createEvent2(Controller controller) {
        controller.runCommand(new LoginCommand("regigigas@nitndeo.com", "sonicBae"));
        CreateNonTicketedEventCommand ntEvent = new CreateNonTicketedEventCommand("We Bought Sonic", EventType.Sports);
        controller.runCommand(ntEvent);
        controller.runCommand(new LogoutCommand());
        return ntEvent.getResult();
    }

    private Long createEvent3(Controller controller) {
        controller.runCommand(new LoginCommand("regigigas@nitndeo.com", "sonicBae"));
        CreateNonTicketedEventCommand ntEvent = new CreateNonTicketedEventCommand("Announcement: Activision owns Nintendo now", EventType.Theatre);
        controller.runCommand(ntEvent);
        controller.runCommand(new LogoutCommand());
        return ntEvent.getResult();
    }

    private boolean cancelEvent(Controller controller, long eventNumber, String organiserMessage) {
        CancelEventCommand cancelCommand = new CancelEventCommand(eventNumber, organiserMessage);
        controller.runCommand(cancelCommand);
        return cancelCommand.getResult();
    }

    @Test
    void cancelEventTest() {
        Controller controller = new Controller();
        registerUsers(controller);
        Long eventNumber = createEvent1(controller);

        // Valid ent provider login
        controller.runCommand(new LoginCommand("regigigas@nitndeo.com", "sonicBae"));

        // cancelling event that actually exists, with a valid cancellation message
        assertTrue(cancelEvent(controller, eventNumber, "I'm so sowwy uWu :3"));

        // cancelling non-existent event
        //TODO This is causing a null pointer exception
        assertFalse(cancelEvent(controller, 69, "lol"));

        // cancelling existing event but with null / blank organiser message
        eventNumber = createEvent2(controller);
        assertFalse(cancelEvent(controller, eventNumber, null));
        assertFalse(cancelEvent(controller, eventNumber, ""));

        // attempting to cancel event as a non-existent user
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand("fake@user.com", "IDontExistLol"));

        eventNumber = createEvent3(controller);
        assertFalse(cancelEvent(controller, eventNumber, "good news: activision doesn't own nintendo!"));

        // non entertaiment provider tries to cancel event
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new LoginCommand("n.provider@noevents.com", "eventsn't"));

        assertFalse(cancelEvent(controller, eventNumber, "i'm committing fraud"));

    }
}
