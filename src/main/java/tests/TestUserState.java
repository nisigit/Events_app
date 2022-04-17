package tests;

import logging.Logger;
import model.Consumer;
import model.EntertainmentProvider;
import model.GovernmentRepresentative;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import state.UserState;

import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

public class TestUserState {
    private Consumer consumer;
    private EntertainmentProvider provider;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void testConstructorNoArgs() {
        UserState state = new UserState();
        HashMap<String, User> hashMap = new HashMap<>();
        hashMap.put("margaret.thatcher@gov.uk",
                new GovernmentRepresentative("margaret.thatcher@gov.uk", "The Good times  ", "margaret.thatcher@gov.uk"));

        assertEquals(state.getAllUsers(), hashMap);
        assertNull(state.getCurrentUser());
    }

    private void addUsersToState(UserState state) {
        consumer = new Consumer(
                "Aper Son",
                "human@being.com",
                "0000000",
                "IAmAGuy",
                "humanpayment@money.com");

        provider = new EntertainmentProvider(
                "Marvel Studios",
                "New York probably",
                "kebinfeeg@bing.com",
                "Kabro Fug",
                "kebinfeeg@bing.com",
                "synder4eva",
                new ArrayList<>(), new ArrayList<>());

        state.addUser(consumer);
        state.addUser(provider);
        state.setCurrentUser(consumer);
    }

    @Test
    void addUserTest() {
        UserState state = new UserState();
        addUsersToState(state);
        HashMap<String, User> map = (HashMap<String, User>) state.getAllUsers();

        assertTrue(map.containsKey("human@being.com"), "this email should be stored in the hashmap but isn't");
        assertTrue(map.containsValue(consumer), "this user should be stored in the hashmap but isn't");

        assertTrue(map.containsKey("kebinfeeg@bing.com"), "this email should be stored in the hashmap but isn't");
        assertTrue(map.containsValue(provider), "this user should be stored in the hashmap but isn't");
    }

    @Test
    void testCopyConstructor() {
        UserState state = new UserState();
        addUsersToState(state);
        UserState copyState = new UserState(state);

        assertEquals(state, copyState, "should have the same values");
        assertNotSame(state, copyState, "should be a DEEP copy, not a shallow one");
    }

    @Test
    void getSetCurrentUserTest() {
        UserState state = new UserState();
        addUsersToState(state);

        state.setCurrentUser(provider);
        assertEquals(state.getCurrentUser(), provider, "current user has been set incorrectly");
    }
}
