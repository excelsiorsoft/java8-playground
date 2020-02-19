package com.excelsiorsoft.validations.ex2;

import static com.excelsiorsoft.validations.ex2.CustomerValidator.isAnAdult;
import static com.excelsiorsoft.validations.ex2.CustomerValidator.isEmailValid;
import static com.excelsiorsoft.validations.ex2.CustomerValidator.isPhoneNumberValid;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excelsiorsoft.validations.ex2.CustomerValidator.ValidationResult;

public class CustomerValidatorTest {

	@Test
	public void test() {
        Customer customer = new Customer(
                "Alice",
                "alice@gmail.com",
                "+0898787879878",
                LocalDate.of(2015, 1,1)
        );


        // Using combinator pattern
        ValidationResult result = isEmailValid()
                .and(isPhoneNumberValid())
                .and(isAnAdult())
                .apply(customer);

        assertNotEquals(result, ValidationResult.SUCCESS);
        
  
	}
	
	

}
