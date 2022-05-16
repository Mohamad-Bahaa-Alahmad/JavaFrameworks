package be.ehb.javaframeworks.examen20192020.service;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import be.ehb.javaframeworks.examen20192020.model.Person;
import be.ehb.javaframeworks.examen20192020.repository.AuctionBidRepository;
import be.ehb.javaframeworks.examen20192020.repository.AuctionRepository;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidAuctionDateTimeException;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidBidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {


    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionBidRepository auctionBidRepository;

    private FakeMailService mailService;

    @BeforeEach
    void setUp() {
        mailService = new FakeMailService();
        auctionService = new AuctionService(auctionRepository, auctionBidRepository, mailService);
    }

    @Test
    void givenAnAuctionWithEndTimeYesterday_whenCreating_expectException() {
        Auction auction = new Auction("Test", 20, new Person("123-46-789", "dirk", "dirk@gmail.com"), LocalDateTime.now().minusDays(1));

        assertThrows(InvalidAuctionDateTimeException.class, () -> auctionService.save(auction));
    }

    @Test
    void givenAnAuctionWithEndTimeTomorrow_whenCreating_expectSaveCalled() {
        Auction auction = new Auction("Test", 20, new Person("123-46-789", "dirk", "dirk@gmail.com"), LocalDateTime.now().plusDays(1));

        auctionService.save(auction);

        verify(auctionRepository).save(auction);
    }

    @Test
    void givenAnAuction_whenPersonBidsOnHisOwnAuction_expectFraudEmail() {
        Person dirk = new Person("123-46-789", "dirk", "dirk@gmail.com");
        Auction auction = new Auction("Test", 20, dirk, LocalDateTime.now().plusDays(5));
        auction.setId(1);
        when(auctionRepository.findById(1)).thenReturn(Optional.of(auction));

        assertThrows(InvalidBidException.class, () -> auctionService.bidOnAuction(1, new AuctionBid(25, auction, dirk)));
        assertEquals(1, mailService.getEmails().size());
    }
}