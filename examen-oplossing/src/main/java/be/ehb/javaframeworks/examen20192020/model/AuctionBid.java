package be.ehb.javaframeworks.examen20192020.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "auction_bids")
public class AuctionBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price", nullable = false)
    private @NotNull double price;

    @JoinColumn(name = "auction_id")
    @ManyToOne
    @JsonIgnore
    private Auction auction;

    @JoinColumn(name = "person_id")
    @ManyToOne
    private @NotNull Person person;

    public AuctionBid() {
    }

    public AuctionBid(@NotNull double price, Auction auction, @NotNull Person person) {
        this.price = price;
        this.auction = auction;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
