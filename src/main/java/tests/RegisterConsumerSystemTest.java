package tests;

import command.LogoutCommand;
import command.RegisterConsumerCommand;
import controller.Controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterConsumerSystemTest {

    private static void register5Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "John Smith",
                "john.smith@example.com",
                "07834281011",
                "Johnny@552",
                "john.smith@example.com"
        ));
        controller.runCommand(new LogoutCommand());

        controller.runCommand(new RegisterConsumerCommand(
                "Jane Giantsdottir",
                "jane@inf.ed.ac.uk",
                "04462187232",
                "giantsRverycool",
                "jane@aol.com"
        ));
        controller.runCommand(new LogoutCommand());

        controller.runCommand(new RegisterConsumerCommand(
                "Boris Thompson",
                "cant-talk-discord-only@yahoo.co.uk",
                "-",
                "password",
                "cant-talk-discord-only@yahoo.co.uk"
        ));

        controller.runCommand(new RegisterConsumerCommand(
                "Wednesday Kebede",
                "i-will-kick-your@gmail.com",
                "-",
                "it is wednesday my dudes",
                "i-will-kick-your@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());

        controller.runCommand(new RegisterConsumerCommand(
                "Joe Root",
                "joe.root@ecb.co.uk",
                "-",
                "win the ashes",
                "joe.root@ecb.co.uk"
        ));
        controller.runCommand(new LogoutCommand());
    }

    @Test
    void getRegisteredConsumers() {
        Controller controller = new Controller();
        register5Consumers(controller);
    }
}
