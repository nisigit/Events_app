package tests;

import command.RegisterConsumerCommand;
import controller.Context;
import model.Consumer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterConsumerSystemTest {
    // TODO: convert to use context, split into smaller methods
    @Test
    void registerConsumerTest() {
        Context context = new Context();

        RegisterConsumerCommand registerConsumerCommand = new RegisterConsumerCommand(
                "Joe Root",
                "joe.root@ecb.co.uk",
                "-",
                "win_the_ashes",
                "payments@specsavers.co.uk"
        );

        registerConsumerCommand.execute(context);
        Consumer consumer = registerConsumerCommand.getResult();

        assertTrue(context.getUserState().getAllUsers().containsValue(consumer));
        assertSame(context.getUserState().getCurrentUser(), consumer);
    }

}
