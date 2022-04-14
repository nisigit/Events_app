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
        assertEquals(sponsorshipState0.getAllSponsorshipRequests(), List.of());
        assertEquals(sponsorshipState0.getPendingSponsorshipRequests(), List.of());
        assertEquals(sponsorshipState0.getNextRequestNumber(), 1);

        SponsorshipState sponsorshipState1 = new SponsorshipState(sponsorshipState0);
        assertEquals(sponsorshipState1, sponsorshipState0);
        assertNotSame(sponsorshipState1, sponsorshipState0);
    }

    @Test
    void testAddSponsorshipRequest() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest11 = new SponsorshipRequest(2, ticketedEvent2);
        SponsorshipRequest sponsorshipRequest01 = new SponsorshipRequest(1, ticketedEvent1);
        assertEquals(sponsorshipRequest0, sponsorshipRequest01);
        assertEquals(sponsorshipRequest1, sponsorshipRequest11);
    }

    @Test
    void testGetAllSponsorshipRequests() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        List<SponsorshipRequest> expectedSponsorships = List.of(sponsorshipRequest0, sponsorshipRequest1);
        assertEquals(sponsorshipState0.getAllSponsorshipRequests(), expectedSponsorships);
    }

    @Test
    void testGetPendingSponsorshipRequests() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        sponsorshipRequest0.accept(20, "ImGovernment@scot.com");
        sponsorshipRequest2.reject();
        assertEquals(sponsorshipState0.getPendingSponsorshipRequests(), List.of(sponsorshipRequest1));
    }

    @Test
    void testFindRequestByNumber() {
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        assertEquals(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(1)), sponsorshipRequest0);
        assertEquals(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(2)), sponsorshipRequest1);
        assertEquals(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(3)), sponsorshipRequest2);
        assertNull(sponsorshipState0.findRequestByNumber(Integer.toUnsignedLong(5)));
    }

    @Test
    void testGetNextRequestNumber() {
        assertEquals(sponsorshipState0.getNextRequestNumber(), 1);
        SponsorshipRequest sponsorshipRequest0 = sponsorshipState0.addSponsorshipRequest(ticketedEvent1);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 2);
        SponsorshipRequest sponsorshipRequest1 = sponsorshipState0.addSponsorshipRequest(ticketedEvent2);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 3);
        SponsorshipRequest sponsorshipRequest2 = sponsorshipState0.addSponsorshipRequest(ticketedEvent3);
        assertEquals(sponsorshipState0.getNextRequestNumber(), 4);
    }
}
