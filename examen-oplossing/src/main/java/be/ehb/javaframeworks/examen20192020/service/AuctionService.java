package be.ehb.javaframeworks.examen20192020.service;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import be.ehb.javaframeworks.examen20192020.repository.AuctionBidRepository;
import be.ehb.javaframeworks.examen20192020.repository.AuctionRepository;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidAuctionDateTimeException;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidBidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository auctionBidRepository;
    private final MailService mailService;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, AuctionBidRepository auctionBidRepository, MailService mailService) {
        this.auctionRepository = auctionRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.mailService = mailService;
    }

    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    public Auction save(Auction auction) {
        LocalDateTime now = LocalDateTime.now();
        if (auction.getEndTime().isBefore(now)) {
            throw new InvalidAuctionDateTimeException("The auction end time is in the past. Invalid auction creation.");
        }

        return auctionRepository.save(auction);
    }


    public void bidOnAuction(int auctionId, AuctionBid auctionBid) {
        Optional<Auction> byId = auctionRepository.findById(auctionId);
        Auction auction = byId.orElseThrow(() -> new InvalidBidException("Auction id does not exist"));
        auctionBid.setAuction(auction);
        LocalDateTime now = LocalDateTime.now();

        if (auction.getEndTime().isBefore(now)) {
            throw new InvalidBidException(("Auction has ended. No more bids allowed"));
        }

        if (auctionBid.getPerson().equals(auction.getPerson())) {
            mailService.sendEmail("Fraud detected for user: " + auction.getPerson().getEmailAddress(), "fraud@auctionhouse.com");
            throw new InvalidBidException("You cannot bid on your own auction!");
        }

        List<AuctionBid> allBidsForAuction = auctionBidRepository.findAllByAuction_Id(auctionId);

        // When no bids present
        if (allBidsForAuction.isEmpty()) {
            if (!(auctionBid.getPrice() > auction.getStartPrice())) {
                throw new InvalidBidException("Bid is lower then start price!");
            }

            auctionBidRepository.save(auctionBid);
            return;
        }

        // when bids already exist
        if (!isNewBidHigherThenHighestExisting(allBidsForAuction, auctionBid)) {
            throw new InvalidBidException("An existing bid with higher amount already exists");
        }

        auctionBidRepository.save(auctionBid);
    }

    private boolean isNewBidHigherThenHighestExisting(List<AuctionBid> allBidsForAuction, AuctionBid auctionBid) {
        double highestBid = allBidsForAuction.stream().map(AuctionBid::getPrice).max(Double::compareTo).get();
        return auctionBid.getPrice() > highestBid;
    }

    public List<AuctionBid> getAllBidsForAuctionId(int auctionId) {
        return auctionBidRepository.findAllByAuction_Id(auctionId);
    }
}
