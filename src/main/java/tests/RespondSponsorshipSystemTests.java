package tests;

import command.*;
import controller.Context;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.testng.Assert.*;

public class RespondSponsorshipSystemTests {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    private void createUsers(Context context) {
        Consumer consumer = new Consumer(
                "Cristina Alexandru",
                "unhealthy-sleep-schedule@ed.ac.uk",
                "123456789",
                "Incorrect",
                "sepp@paypal.com");

        EntertainmentProvider provider = new EntertainmentProvider(
                "Edinburgh University Indian Society",
                "indiansocediuni@gmail.com",
                "indiansoc.eusa@crypto.org",
                "Simran Punjabi",
                "indiansocediuni@gmail.com",
                "br0wn/t0wn",
                List.of("Isha Pawar", "Shivie Choudhry", "Diya Grace Jacob"),
                List.of("i.pawar@ed.ac.uk", "m.s.choudhry@ed.ac.uk", "dont-contact-me@im-so-tired.com"));

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

    private Long createEvent3(Context context) {
        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        CreateTicketedEventCommand eventCommand = new CreateTicketedEventCommand("Holi celebration",
                EventType.Dance,
                120,
                1,
                true);

        eventCommand.execute(context);

        LogoutCommand logout = new LogoutCommand();
        logout.execute(context);

        return eventCommand.getResult();
    }

    @Test
    void validSponsorshipAcceptance() {
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
        assertTrue(accept.getResult(), "response attempt failed despite all conditions being valid");

        // check if request has actually been approved within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.ACCEPTED, x.getStatus(),
                        "sponsorship status not logged as accepted in SponsorshipState");
            }
        }
    }

    @Test
    void validFullSponsorship() {
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

        RespondSponsorshipCommand accept = new RespondSponsorshipCommand(requestNumber, 100);
        accept.execute(context);

        // check if result is returned correctly
        assertTrue(accept.getResult(), "response attempt failed despite all conditions being valid");

        // check if request has actually been approved within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.ACCEPTED, x.getStatus(),
                        "sponsorship status not logged as accepted in SponsorshipState");
            }
        }
    }

    @Test
    void validSponsorshipRejection() {
        Context context = new Context();

        createUsers(context);

        LoginCommand login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        login.execute(context);

        // valid rejection attempt from govt rep
        long eventNumber = createEvent2(context);

        login.execute(context);

        long requestNumber = 0;
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        RespondSponsorshipCommand reject = new RespondSponsorshipCommand(requestNumber, 0);
        reject.execute(context);

        // check if result returned correctly
        assertTrue(reject.getResult(), "response attempt failed despite all conditions being valid");

        // check if request has actually been rejected within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.REJECTED, x.getStatus(),
                        "sponsorship status not logged as rejected in SponsorshipState");
            }
        }
    }

    @Test
    void invalidConsumerResponse() {
        Context context = new Context();

        createUsers(context);

        // invalid response attempt from consumer
        long eventNumber = createEvent3(context);

        LoginCommand login = new LoginCommand("unhealthy-sleep-schedule@ed.ac.uk", "Incorrect");
        login.execute(context);

        long requestNumber = 0;
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        RespondSponsorshipCommand invalidConsumer = new RespondSponsorshipCommand(requestNumber, 11);
        invalidConsumer.execute(context);

        // check if result returned correctly
        assertFalse(invalidConsumer.getResult(), "sponsorship response successful despite the user being a consumer");

        // edge cases
        invalidConsumer = new RespondSponsorshipCommand(requestNumber, 0);
        invalidConsumer.execute(context);

        assertFalse(invalidConsumer.getResult(),
                "sponsorship response successful despite the user being a consumer, when attempting to reject");

        invalidConsumer = new RespondSponsorshipCommand(requestNumber, 100);
        invalidConsumer.execute(context);

        assertFalse(invalidConsumer.getResult(),
                "sponsorship response successful despite the user being a consumer, when attempting to fully sponsor");

        // check if request is still 'Pending' within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.PENDING, x.getStatus());
            }
        }
    }

    @Test
    void invalidEntProviderResponse() {
        Context context = new Context();

        createUsers(context);

        // invalid response attempt from consumer
        long eventNumber = createEvent3(context);

        LoginCommand login = new LoginCommand("indiansocediuni@gmail.com", "br0wn/t0wn");
        login.execute(context);

        long requestNumber = 0;
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        RespondSponsorshipCommand invalidEntProvider = new RespondSponsorshipCommand(requestNumber, 11);
        invalidEntProvider.execute(context);

        // check if result returned correctly
        assertFalse(invalidEntProvider.getResult(), "sponsorship response successful despite the user being an entertainment provider");

        // edge cases
        invalidEntProvider = new RespondSponsorshipCommand(requestNumber, 0);
        invalidEntProvider.execute(context);

        assertFalse(invalidEntProvider.getResult(),
                "sponsorship response successful despite the user being a provider, when attempting to reject");

        invalidEntProvider = new RespondSponsorshipCommand(requestNumber, 100);
        invalidEntProvider.execute(context);

        assertFalse(invalidEntProvider.getResult(),
                "sponsorship response successful despite the user being a provider, when attempting to fully sponsor");

        // check if request is still 'Pending' within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.PENDING, x.getStatus());
            }
        }
    }

    @Test
    void incorrectPercentageResponse() {
        Context context = new Context();

        createUsers(context);

        // invalid response attempt from consumer
        long eventNumber = createEvent3(context);

        long requestNumber = 0;
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) { requestNumber = x.getRequestNumber(); }
        }

        // invalid response attempt from government provider (wrong percentage)
        LoginCommand login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        login.execute(context);

        RespondSponsorshipCommand negativePercent = new RespondSponsorshipCommand(requestNumber, -1);
        negativePercent.execute(context);

        // check if result returned correctly
        assertFalse(negativePercent.getResult(), "sponsorship response successful despite percentage being negative");

        // check if request is still 'Pending' within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.PENDING, x.getStatus());
            }
        }

        RespondSponsorshipCommand percentMoreThan100 = new RespondSponsorshipCommand(requestNumber, 110);
        percentMoreThan100.execute(context);

        // check if result returned correctly
        assertFalse(percentMoreThan100.getResult(), "sponsorship response successful despite percentage being >100");

        // check if request is still 'Pending' within SponsorshipState
        for (SponsorshipRequest x : context.getSponsorshipState().getAllSponsorshipRequests()) {
            if (x.getEvent().getEventNumber() == eventNumber) {
                assertEquals(SponsorshipStatus.PENDING, x.getStatus());
            }
        }
    }

}
