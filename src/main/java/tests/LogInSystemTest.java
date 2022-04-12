package tests;

import command.LoginCommand;
import controller.Context;
import logging.Logger;
import model.Consumer;
import model.EntertainmentProvider;
import model.GovernmentRepresentative;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;

public class LogInSystemTest {
    private Consumer consumer;
    private EntertainmentProvider provider;
    private GovernmentRepresentative govtRep;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private void createUsers(Context context) {
        this.consumer = new Consumer("Con Sumer",
                "c.sumer@customer.com",
                "123456789",
                "SpendingMoneyIsSwag",
                "c.sumer@customer.com");
        this.provider = new EntertainmentProvider("MakesEvents LLC",
                "Earth, Solar System, Milky Way Galaxy",
                "makeseventsllc@paypal.com",
                "Enter Tainmentpro Vider",
                "e.vider@makeseventsllc.ac.uk",
                "saregamapadhanisa", new ArrayList<>(), new ArrayList<>());
        this.govtRep = new GovernmentRepresentative("margaret.thatcher@gov.uk",
                "The Good times  ",
                "margaret.thatcher@gov.uk");

        context.getUserState().addUser(this.consumer);
        context.getUserState().addUser(this.provider);
    }

    @Test
    void loginConsumer() {
        Context context = new Context();
        createUsers(context);

        // valid consumer login attempt
        LoginCommand login = new LoginCommand("c.sumer@customer.com", "SpendingMoneyIsSwag");
        login.execute(context);

        assertEquals(login.getResult(), this.consumer);
        assertEquals(context.getUserState().getCurrentUser(), this.consumer);
    }

    @Test
    void loginEntertainmentProvider() {
        Context context = new Context();
        createUsers(context);

        // valid entertainment provider login attempt
        LoginCommand login = new LoginCommand("e.vider@makeseventsllc.ac.uk", "saregamapadhanisa");
        login.execute(context);

        assertEquals(login.getResult(), this.provider);
        assertEquals(context.getUserState().getCurrentUser(), this.provider);
    }

    @Test
    void loginGovernmentRep() {
        Context context = new Context();
        createUsers(context);

        // valid government rep login attempt
        LoginCommand login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        login.execute(context);

        assertEquals(login.getResult(), this.govtRep);
        assertEquals(context.getUserState().getCurrentUser(), this.govtRep);
    }

    @Test
    void invalidLogin() {
        Context context = new Context();
        createUsers(context);

        // invalid user login
        LoginCommand login = new LoginCommand("dontexist@lol.uk", "incorporeal");
        login.execute(context);

        assertNull(login.getResult());
        assertNull(context.getUserState().getCurrentUser());
    }
}
