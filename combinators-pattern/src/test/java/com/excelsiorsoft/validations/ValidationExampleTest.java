package com.excelsiorsoft.validations;

import static com.excelsiorsoft.validations.ValidationExample.UserValidation.eMailContainsAtSign;
import static com.excelsiorsoft.validations.ValidationExample.UserValidation.nameIsNotEmpty;
import static com.excelsiorsoft.validations.ValidationExample.WebValidation.all;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.excelsiorsoft.validations.ValidationExample.UserValidation;
import com.excelsiorsoft.validations.ValidationExample.ValidationResult;

import static com.excelsiorsoft.validations.ValidationExample.ValidationResult.invalid;
import static com.excelsiorsoft.validations.ValidationExample.ValidationResult.valid;

import static org.hamcrest.CoreMatchers.is;


public class ValidationExampleTest {

	@Test
    public void yield_valid_for_user_with_non_empty_name_and_mail_with_at_sign(){
        final UserValidation validation = nameIsNotEmpty().and(eMailContainsAtSign());
        final User gregor = new User("Gregor", 30, "mail@mailinator.com");

        assertThat(validation.apply(gregor), is(valid()));
    }

    @Test
    public void yield_invalid_for_user_with_empty_name(){
        final UserValidation validation = nameIsNotEmpty().and(eMailContainsAtSign());
        final User gregor = new User("", 30, "mail@mailinator.com");

        final ValidationResult result = validation.apply(gregor);
        assertThat(result, is(invalid("Name is empty.")));
        result.getReason().ifPresent(System.out::println);
    }

    @Test
    public void yield_invalid_for_user_with_mail_missing_at_sign(){
        final UserValidation validation = nameIsNotEmpty().and(eMailContainsAtSign());
        final User gregor = new User("Gregor", 30, "mailmailinator.com");

        assertThat(validation.apply(gregor), is(invalid("Missing @-sign in E-Mail.")));
    }

    @Test
    public void yield_invalid_with_two_reasons(){
        final UserValidation validation = all(nameIsNotEmpty(), eMailContainsAtSign());
        final User gregor = new User("", 30, "mailmailinator.com");

        assertThat(validation.apply(gregor), is(invalid("Name is empty.\nMissing @-sign in E-Mail.")));
    }

}
