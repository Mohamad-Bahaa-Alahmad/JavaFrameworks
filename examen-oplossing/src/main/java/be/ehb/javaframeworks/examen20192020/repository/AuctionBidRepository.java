package be.ehb.javaframeworks.examen20192020.repository;

import be.ehb.javaframeworks.examen20192020.model.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, Integer> {

    List<AuctionBid> findAllByAuction_Id(int auctionId);

}
