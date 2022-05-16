package be.ehb.javaframeworks.examen20192020.controller;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import be.ehb.javaframeworks.examen20192020.service.AuctionService;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidAuctionDateTimeException;
import be.ehb.javaframeworks.examen20192020.service.exception.InvalidBidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    List<Auction> findAll() {
        return auctionService.getAll();
    }

    @PostMapping
    Auction save(@Valid @RequestBody Auction auction) {
        return auctionService.save(auction);
    }

    @PostMapping("/{id}/bids")
    void bidOnAuction(@PathVariable("id") int auctionId, @Valid @RequestBody AuctionBid auctionBid) {
        auctionService.bidOnAuction(auctionId, auctionBid);
    }

    @GetMapping("/{id}/bids")
    List<AuctionBid> bidOnAuction(@PathVariable("id") int auctionId) {
        return auctionService.getAllBidsForAuctionId(auctionId);
    }

    @ExceptionHandler(InvalidAuctionDateTimeException.class)
    ResponseEntity<String> handleInvalidAuction(InvalidAuctionDateTimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBidException.class)
    ResponseEntity<String> handleInvalidAuctionBid(InvalidBidException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
