package be.ehb.javaframeworks.examen20192020.service.exception;

public class InvalidAuctionDateTimeException extends RuntimeException {

    public InvalidAuctionDateTimeException(String message) {
        super(message);
    }
}
