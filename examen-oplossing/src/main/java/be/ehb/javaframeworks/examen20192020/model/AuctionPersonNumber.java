package be.ehb.javaframeworks.examen20192020.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AuctionPersonNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuctionPersonNumber {
    String message() default "Invalid Auction Person Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
