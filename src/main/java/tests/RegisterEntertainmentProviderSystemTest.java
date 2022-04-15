package tests;

import command.RegisterEntertainmentProviderCommand;
import controller.Controller;
import model.EntertainmentProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RegisterEntertainmentProviderSystemTest {

    private static EntertainmentProvider add5Providers(Controller controller) {
        RegisterEntertainmentProviderCommand cmd = new RegisterEntertainmentProviderCommand(
                "Maguire Stans",
                "Maguire Building, 15 Holyrood Park Rd.",
                "willem.da-dripping@oscorp.org",
                "Patrick Bateman",
                "p.bateman@pierce&pierce.com",
                "Dorsia@9",
                List.of("Jonah Jamieson", "Norman Osborne"),
                List.of("j.jamieson@dailybugle.com", "norman@oscorp.org")
        );
        controller.runCommand(cmd);
        return cmd.getResult();
    }

    @Test
    void registerNewEntProviderTest() {
        Controller controller = new Controller();
        EntertainmentProvider provider = add5Providers(controller);

        assertNotNull(provider);
        assertEquals("p.bateman@pierce&pierce.com", provider.getEmail());
        assertEquals("Maguire Stans", provider.getOrgName());
        assertEquals("willem.da-dripping@oscorp.org", provider.getPaymentAccountEmail());
    }


}
