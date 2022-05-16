package be.ehb.javaframeworks.examen20192020.service;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import be.ehb.javaframeworks.examen20192020.repository.AuctionBidRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

@Service
public class AuctionManagementService {

    private final AuctionBidRepository auctionBidRepository;
    public static final int ADMINISTRATIVE_UNIT_PRICE = 5;

    public AuctionManagementService(AuctionBidRepository auctionBidRepository) {
        this.auctionBidRepository = auctionBidRepository;
    }

    double getAmountOfEarnedMoney() {
        List<AuctionBid> all = auctionBidRepository.findAll();
        long uniqueAuctions = all.stream().map(x -> x.getAuction().getId()).distinct().count();
        long administrativeMoney = uniqueAuctions * ADMINISTRATIVE_UNIT_PRICE;

        Map<Auction, Optional<AuctionBid>> highestBidsPerAuction = all
                .stream()
                .collect(groupingBy(AuctionBid::getAuction, maxBy(Comparator.comparingDouble(AuctionBid::getPrice))));

        Double percentageMoney = 0.0;
        for (Optional<AuctionBid> auctionBid: highestBidsPerAuction.values()) {
            if (auctionBid.isPresent()) {
                percentageMoney += auctionBid.get().getPrice() * 0.15;
            }
        }

        return administrativeMoney + percentageMoney;
    }
}
