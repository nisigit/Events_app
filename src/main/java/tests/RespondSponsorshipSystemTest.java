package tests;

import command.*;
import controller.Context;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

public class RespondSponsorshipSystemTest {
    private Consumer consumer;
    private EntertainmentProvider provider;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    private void createUsers(Context context) {
        consumer = new Consumer(
                "Cristina Alexandru",
                "unhealthy-sleep-schedule@ed.ac.uk",
                "123456789",
                "Incorrect",
                "sepp@paypal.com");

        provider = new EntertainmentProvider(
                "Edinburgh University Indian Society",
                "indiansocediuni@gmail.com",
                "indiansoc.eusa@crypto.org",
                "Simran Punjabi",
                "indiansocediuni@gmail.com",
                "br0wn/t0wn",
                List.of("Isha Pawar", "Shivie Choudhry", "Diya Grace Jacob"),
                List.of("i.pawar@ed.ac.uk", "m.s.choudhry@ed.ac.uk", "dont-fucking-contact-me@im-so-tired.com"));

        context.getUserState().addUser(consumer);
        context.getUserState().addUser(provider);
    }

    private Long createEvent1(Context context) {
        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        CreateTicketedEventCommand eventCommand = new CreateTicketedEventCommand("Bollywood Night 2022",
                EventType.Dance,
                120,
                5.00,
                true);

        eventCommand.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return eventCommand.getResult();
    }

    private Long createEvent2(Context context) {
        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        CreateTicketedEventCommand eventCommand = new CreateTicketedEventCommand("IPL Screening",
                EventType.Sports,
                120,
                5.00,
                true);

        eventCommand.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return eventCommand.getResult();
    }

    @Test
    void respondSponsorshipTest() {
        Context context = new Context();

        createUsers(context);

        // valid acceptance attempt from govt rep
        long eventNumber = createEvent1(context);

        LoginCommand login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        login.execute(context);

        long requestNumber = 0;
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        RespondSponsorshipCommand accept = new RespondSponsorshipCommand(requestNumber, 50);
        accept.execute(context);

        // check if result is returned correctly
        assertTrue(accept.getResult());

        // check if request has actually been approved within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.ACCEPTED, x.getStatus());
            }
        }

        // valid rejection attempt from govt rep
        eventNumber = createEvent2(context);

        login.execute(context);

        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        RespondSponsorshipCommand reject = new RespondSponsorshipCommand(requestNumber, 0);
        reject.execute(context);

        // check if result returned correctly
        assertTrue(reject.getResult());

        // check if request has actually been rejected within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.REJECTED, x.getStatus());
            }
        }

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        
    }
}
