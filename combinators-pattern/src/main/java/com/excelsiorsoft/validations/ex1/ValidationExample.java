/**
 * 
 */
package com.excelsiorsoft.validations.ex1;

import static com.excelsiorsoft.validations.ex1.ValidationExample.ValidationResult.invalid;
import static com.excelsiorsoft.validations.ex1.ValidationExample.ValidationResult.valid;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Simeon
 *
 */
public class ValidationExample {

	interface ValidationResult{
        static ValidationResult valid(){
            return ValidationSupport.valid();
        }
        static ValidationResult invalid(String reason){
            return new Invalid(reason);
        }
        boolean isValid();
        Optional<String> getReason();
    }
    
    
    private final static class Invalid implements ValidationResult {

        private final String reason;

        Invalid(String reason){
            this.reason = reason;
        }

        public boolean isValid(){
            return false;
        }

        public Optional<String> getReason(){
            return Optional.of(reason);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Invalid invalid = (Invalid) o;
            return Objects.equals(reason, invalid.reason);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reason);
        }
        @Override
        public String toString() {
            return "Invalid[" +
                    "reason='" + reason + '\'' +
                    ']';
        }
    }

    private static final class ValidationSupport {
        private static final ValidationResult valid = new ValidationResult(){
            public boolean isValid(){ return true; }
            public Optional<String> getReason(){ return Optional.empty(); }
        };

        static ValidationResult valid(){
            return valid;
        }
    }
    
    
    interface UserValidation extends Function<User, ValidationResult> {
        static UserValidation nameIsNotEmpty() {
            return holds(user -> !user.name.trim().isEmpty(), "Name is empty.");
        }

        static UserValidation eMailContainsAtSign() {
            return holds(user -> user.email.contains("@"), "Missing @-sign in E-Mail.");
        }

        static UserValidation holds(Predicate<User> p, String message){
            return user -> p.test(user) ? valid() : invalid(message);
        }

        default UserValidation and(UserValidation other) {
            return user -> {
                final ValidationResult result = this.apply(user);
                return result.isValid() ? other.apply(user) : result;
            };
        }
    }
    
    
    interface WebValidation {
        static UserValidation all(UserValidation... validations){
            return user -> {
                String reasons = Arrays.stream(validations)
                      .map(v -> v.apply(user))
                      .filter(r -> !r.isValid())
                      .map(r -> r.getReason().get())
                      .collect(Collectors.joining("\n"));
                return reasons.isEmpty()?valid():invalid(reasons);
            };
        }
    }


}
