package tests;

import command.LoginCommand;
import command.LogoutCommand;
import command.RegisterConsumerCommand;
import command.RegisterEntertainmentProviderCommand;
import controller.Controller;
import model.Consumer;
import model.EntertainmentProvider;
import model.GovernmentRepresentative;
import org.junit.jupiter.api.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;

public class LogInSystemTest {
    private Consumer consumer;
    private EntertainmentProvider provider;
    private GovernmentRepresentative govtRep;

    private void registerUsers(Controller controller) {
        LogoutCommand logout = new LogoutCommand();
        controller.runCommand(new RegisterConsumerCommand(
                "Con Sumer",
                "c.sumer@customer.com",
                "123456789",
                "SpendingMoneyIsSwag",
                "c.sumer@customer.com"
        ));
        controller.runCommand(logout);

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "MakesEvents LLC",
                "Earth, Solar System, Milky Way Galaxy",
                "makeseventsllc@paypal.com",
                "Enter Tainmentpro Vider",
                "e.vider@makeseventsllc.ac.uk",
                "saregamapadhanisa",
                new ArrayList<>(), new ArrayList<>()
        ));
        controller.runCommand(logout);
    }

    private void createUserObjects() {
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
    }

    @Test
    void LoginTest() {
        Controller controller = new Controller();
        registerUsers(controller);
        createUserObjects();

        // valid consumer login attempt
        LoginCommand login = new LoginCommand("c.sumer@customer.com", "SpendingMoneyIsSwag");
        controller.runCommand(login);

        assertEquals(login.getResult(), this.consumer);

        // valid entertainment provider login attempt
        login = new LoginCommand("e.vider@makeseventsllc.ac.uk", "saregamapadhanisa");
        controller.runCommand(login);
        assertEquals(login.getResult(), this.provider);

        // valid government rep login attempt
        login = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        controller.runCommand(login);
        //TODO: flags this as false but can't figure out why
        assertEquals(login.getResult(), this.govtRep);

        // invalid login attempt
        login = new LoginCommand("dontexist@lol.uk", "incorporeal");
        controller.runCommand(login);
        assertNull(login.getResult());
    }
}
