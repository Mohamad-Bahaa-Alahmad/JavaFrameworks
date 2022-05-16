package be.ehb.javaframeworks.examen20192020.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuctionPersonNumberValidator implements ConstraintValidator<AuctionPersonNumber, String> {

    @Override
    public void initialize(AuctionPersonNumber constraint) { }

    @Override
    public boolean isValid(String auctionPersonNumber, ConstraintValidatorContext context) {
        if (auctionPersonNumber == null || auctionPersonNumber.length() != 11) {
            return false;
        }

        char[] chars = auctionPersonNumber.toCharArray();
        if (chars[3] != '-' || chars[7] != '-') {
            return false;
        }

        String s = auctionPersonNumber.replaceAll("-", "");
        for (char c: s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
