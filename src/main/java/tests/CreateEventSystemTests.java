package tests;

import command.CreateTicketedEventCommand;
import command.LoginCommand;
import controller.Context;
import logging.Logger;
import model.Consumer;
import model.EntertainmentProvider;
import model.EventType;
import model.GovernmentRepresentative;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreateEventSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private void registerUsers(Context context) {
        EntertainmentProvider provider1 = new EntertainmentProvider(
                "Maguire Stans",
                "Maguire Building, 15 Holyrood Park Rd.",
                "willem.da-dripping@dailybugle.com",
                "Patrick Bateman",
                "p.bateman@pierce@pierce.com",
                "Dorsia@9",
                List.of("Jonah Jamieson", "Norman Osborn"),
                List.of("j.jamieson@dailybugle.com", "norman@oscorp.org")
        );

        EntertainmentProvider provider2 = new EntertainmentProvider(
                "Edinburgh University Indian Society",
                "indiansocediuni@gmail.com",
                "indiansoc.eusa@crypto.org",
                "Simran Punjabi",
                "indiansocediuni@gmail.com",
                "br0wn/t0wn",
                List.of("Isha Pawar", "Shivie Choudhry", "Diya Grace Jacob"),
                List.of("i.pawar@ed.ac.uk", "m.s.choudhry@ed.ac.uk", "dont-contact-me@im-so-tired.com"));

        Consumer consumer = new Consumer(
                "Not Aprovider",
                "n.provider@noevents.com",
                "69696969696",
                "eventsn't",
                "n.provider@noevents.com");

        GovernmentRepresentative governmentRepresentative = new GovernmentRepresentative(
                "n.sturgeon@gov.scot",
                "scexit2022",
                "sturgeon@gofundme.org"
        );

        context.getUserState().addUser(provider1);
        context.getUserState().addUser(provider2);
        context.getUserState().addUser(consumer);
        context.getUserState().addUser(governmentRepresentative);
    }

    @Test
    void repCantMakeEvent() {
        Context context = new Context();
        registerUsers(context);
        LoginCommand loginCommand = new LoginCommand("n.sturgeon@gov.scot", "scexit2022");
        loginCommand.execute(context);

        CreateTicketedEventCommand createTicketedEventCommand = new CreateTicketedEventCommand(
                "BOTW 2 release party", EventType.Dance,
                200, 12.00, true);

        createTicketedEventCommand.execute(context);
        assertNull(createTicketedEventCommand.getResult(), "government provider has managed to make event when they shouldn't be able to");
    }

    @Test
    void consumerCantMakeEvent() {
        Context context = new Context();
        registerUsers(context);
        LoginCommand loginCommand = new LoginCommand("n.provider@noevents.com", "eventsn't");
        loginCommand.execute(context);

        CreateTicketedEventCommand createTicketedEventCommand = new CreateTicketedEventCommand(
                "BOTW 2 release party", EventType.Dance,
                200, 12.00, true);
        createTicketedEventCommand.execute(context);
        assertNull(createTicketedEventCommand.getResult(), "consumer has managed to create event when they shouldn't be able to");
    }

    @Test
    void validEventCreation() {
        Context context = new Context();
        registerUsers(context);
        LoginCommand loginCommand = new LoginCommand("p.bateman@pierce@pierce.com", "Dorsia@9");
        loginCommand.execute(context);

        CreateTicketedEventCommand cmd = new CreateTicketedEventCommand(
                "Spider-Man 4: Ultimate Depression",
                EventType.Movie,
                200,
                12,
                false
        );
        cmd.execute(context);
        Long eventNum = cmd.getResult();
        assertEquals(1, eventNum, "event creation has failed even though all parameters were correct");
    }
}
