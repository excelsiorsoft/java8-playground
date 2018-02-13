package streams;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.SoftAssertionError;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.internal.Objects;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

public class ChainedSoftAssertionTest {

  private EmailSoftAssertions softly = new EmailSoftAssertions();

  @Test
  public void should_work_with_chained_assertions() {
    Email email = new Email("from@example.com", "to@example.com", "Message from Sender to Recipient");

    softly.assertThat(email)
      .hasFrom("sender@example.com")
      .hasTo("recipient@example.com")
      .hasSubject("Message from Sender to Recipient")
      .extracting("from", "to")
      .contains("sender@example.com")
      .hasAtLeastOneElementOfType(String.class)
      .doesNotContain("to@example.com");

    try {
      softly.assertAll();
      shouldHaveThrown(SoftAssertionError.class);
    } catch (SoftAssertionError e) {
      assertThat(e.getErrors()).hasSize(4);
    }
  }

  private static class EmailSoftAssertions extends SoftAssertions {

    public EmailAssert assertThat(Email actual) {
      return proxy(EmailAssert.class, Email.class, actual);
    }
  }

  private static class Email {

    private final String from;
    private final String to;
    private final String subject;

    public Email(String from, String to, String subject) {
      this.from = from;
      this.to = to;
      this.subject = subject;
    }

    public String getFrom() {
      return from;
    }

    public String getTo() {
      return to;
    }

    public String getSubject() {
      return subject;
    }
  }

  private static class EmailAssert extends AbstractObjectAssert<EmailAssert, Email> {

    Objects objects = Objects.instance();

    public EmailAssert(Email actual) {
      super(actual, EmailAssert.class);
    }

    public EmailAssert hasFrom(String expectedFrom) {
      objects.assertEqual(info, actual.from, expectedFrom);
      return myself;
    }

    public EmailAssert hasTo(String expectedFrom) {
      objects.assertEqual(info, actual.to, expectedFrom);
      return myself;
    }

    public EmailAssert hasSubject(String expectedFrom) {
      objects.assertEqual(info, actual.subject, expectedFrom);
      return myself;
    }
  }

}