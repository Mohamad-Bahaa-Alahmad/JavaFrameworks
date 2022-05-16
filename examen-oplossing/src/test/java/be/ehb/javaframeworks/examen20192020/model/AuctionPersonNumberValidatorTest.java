package be.ehb.javaframeworks.examen20192020.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuctionPersonNumberValidatorTest {

    static AuctionPersonNumberValidator auctionPersonNumberValidator;

    @BeforeAll
    static void setUp() {
        auctionPersonNumberValidator = new AuctionPersonNumberValidator();
    }

    @Test
    void givenAValidAuctionNumber_expectValid() {
        String s = "123-456-789";
        boolean valid = auctionPersonNumberValidator.isValid(s, null);
        assertTrue(valid);
    }

    @Test
    void givenANullValue_expectInvalid() {
        String s = null;
        boolean valid = auctionPersonNumberValidator.isValid(s, null);
        assertFalse(valid);
    }

    @Test
    void givenALongerAuctionNumber_expectInvalid() {
        String s = "123-456-7891";
        boolean valid = auctionPersonNumberValidator.isValid(s, null);
        assertFalse(valid);
    }

    @Test
    void givenAWrongFormatAuctionNumber_expectInvalid() {
        String s = "1234-567-89";
        boolean valid = auctionPersonNumberValidator.isValid(s, null);
        assertFalse(valid);
    }

    @Test
    void givenAsciiValues_expectInvalid() {
        String s = "abc-def-ghi";
        boolean valid = auctionPersonNumberValidator.isValid(s, null);
        assertFalse(valid);
    }

    @Test
    void givenAnAuctionNumberWithUnderScores_expectInvalid() {
        String s1 = "123_456-789";
        String s2 = "123-456_789";

        boolean valid1 = auctionPersonNumberValidator.isValid(s1, null);
        boolean valid2 = auctionPersonNumberValidator.isValid(s2, null);

        assertFalse(valid1);
        assertFalse(valid2);
    }
}