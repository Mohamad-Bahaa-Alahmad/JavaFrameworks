package be.ehb.javaframeworks.examen20192020.service;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import be.ehb.javaframeworks.examen20192020.model.Person;
import be.ehb.javaframeworks.examen20192020.repository.AuctionBidRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionManagementServiceTest {

    @Mock
    AuctionBidRepository auctionBidRepository;

    private AuctionManagementService auctionManagementService;

    @BeforeEach
    void setUp() {
        auctionManagementService = new AuctionManagementService(auctionBidRepository);
    }

    @Test
    void validateTotalAmountOfEarnedMoneyIsCorrect() {

        Person frank = new Person("123-456-789", "Frank", "frank@gmail.com");
        Auction auction1 = new Auction("auction1", 10, frank, LocalDateTime.now().plusDays(5));
        auction1.setId(1);
        Auction auction2 = new Auction("auction2", 30, frank, LocalDateTime.now().plusDays(5));
        auction2.setId(2);

        Mockito.when(auctionBidRepository.findAll()).thenReturn(Arrays.asList(
                new AuctionBid(40, auction1, frank),
                new AuctionBid(50, auction1, frank),
                new AuctionBid(100, auction2, frank)
        ));

        // 2 unique auctions with bids = 10 euro administrative costs
        // 15% of 50 = 7,5 & 15% of 100 = 15

        Assertions.assertEquals(32.5, auctionManagementService.getAmountOfEarnedMoney());
    }
}