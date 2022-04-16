package tests;

import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import model.SponsorshipRequest;
import model.TicketedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import static org.testng.Assert.*;
import state.SponsorshipState;

import java.util.List;

public class TestSponsorshipState {
    EntertainmentProvider entertainmentProvider;
    TicketedEvent ticketedEvent1, ticketedEvent2, ticketedEvent3;
    SponsorshipState sponsorshipState0;

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @BeforeEach
    void setup() {
        this.entertainmentProvider = new EntertainmentProvider("Uni of Edinburgh", "Old College", "entProviderPaymentEmail@ed.ac.uk", "King Stuart", "KingStuart@ed.ac.uk", "password", List.of("Clark Barwick"), List.of("otheremails@ed.ac.uk"));
        this.ticketedEvent1 = new TicketedEvent(1, entertainmentProvider, "Fei Cheng Wu Rao", EventType.Theatre, 8.88, 200);
        this.ticketedEvent2 = new TicketedEvent(2, entertainmentProvider, "NCAA", EventType.Sports, 20, 2000);
        this.ticketedEvent3 = new TicketedEvent(3, entertainmentProvider, "Shenyang Story", EventType.Movie, 30, 100);
        this.sponsorshipState0 = new SponsorshipState();
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    @Test
    void testConstructors() {
        assertEquals(sponsorshipState0.getAllSponsorshipRequests(), List.of(), "Should be initialized as an empty list");
        assertEquals(sponsorshipState0.getPendingSponsorshipRequests(), List.of(), "Should be initialized as an empty list");
        assertEquals(sponsorshipState0.getNextRequestNumber(), 1, "Should be initialized as 1");

        SponsorshipState sponsorshipState1 = new SponsorshipState(sponsorshipState0);
        assertEquals(sponsorshipState1, sponsorshipState0, "Should be equal with same fields");
        assertNotSame(sponsorshipState1, sponsorshipState0, "Should be a deep copy");
    }

    @Test
    void testAddSponsorshipRequest() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest11 = new SponsorshipRequest(2, ticketedEvent2);
        SponsorshipRequest sponsorshipRequest01 = new SponsorshipRequest(1, ticketedEvent1);
        assertEquals(sponsorshipRequest0, sponsorshipRequest01, "should be equal to the expected object");
        assertEquals(sponsorshipRequest1, sponsorshipRequest11, "should be equal to the expected object");
    }

    @Test
    void testGetAllSponsorshipRequests() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        List<SponsorshipRequest> expectedSponsorships = List.of(sponsorshipRequest0, sponsorshipRequest1);
        assertEquals(sponsorshipState0.getAllSponsorshipRequests(), expectedSponsorships, "should be equal to the expected list");
    }

    @Test
    void testGetPendingSponsorshipRequests() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        sponsorshipRequest0.accept(20, "ImGovernment@scot.com");
        sponsorshipRequest2.reject();
        assertEquals(sponsorshipState0.getPendingSponsorshipRequests(), List.of(sponsorshipRequest1), "Should only contain the second request");
    }

    @Test
    void testFindRequestByNumber() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        assertSame(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(1)), sponsorshipRequest0, "Should be the same object as expected");
        assertSame(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(2)), sponsorshipRequest1, "Should be the same object as expected");
        assertSame(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(3)), sponsorshipRequest2, "Should be the same object as expected");
        assertNull(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(5)), "Should be null with non-existed request number");
    }

    @Test
    void testGetNextRequestNumber() {
        assertEquals(sponsorshipState0.getNextRequestNumber(), 1, "Should be initialized as 1");
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 2, "Should be 2 as 1 request is created");
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 3, "Should be 3 as 2 requests are created");
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 4, "Should be 4 as 3 requests are created");
    }
}
