package be.ehb.javaframeworks.examen20192020.repository;

import be.ehb.javaframeworks.examen20192020.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
}
