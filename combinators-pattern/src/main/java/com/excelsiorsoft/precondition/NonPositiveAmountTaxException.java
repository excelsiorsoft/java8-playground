/**
 * 
 */
package com.excelsiorsoft.precondition;

/**
 * @author Simeon
 *
 */
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class NonPositiveAmountTaxException extends RuntimeException {

	private NonPositiveAmountTaxException(String message) {
		super(message);
	}

	public NonPositiveAmountTaxException(BigDecimal amount) {
		this("Amount to be taxed must be > 0 but was " + amount);
	}
}
