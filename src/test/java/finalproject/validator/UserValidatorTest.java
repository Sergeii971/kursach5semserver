package finalproject.validator;

import com.verbovskiy.server.validator.UserValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorTest {
    @Test
    public void validateEmailPositiveTest() {
        String line = "sergeiverbovskiy4@gmail.com";
        boolean actual = UserValidator.validateEmail(line);
        assertTrue(actual);
    }

    @Test
    public void validateEmailNegativeTest() {
        String line = "sergeiverbovskiy4gmail.com";
        boolean actual = UserValidator.validateEmail(line);
        assertFalse(actual);
    }

    @Test
    public void validatePasswordPositiveTest() {
        String line = "S12345678s&";
        boolean actual = UserValidator.validatePassword(line);
        assertTrue(actual);
    }

    @Test
    public void validatePasswordNegativeTest() {
        String line = "S12345678s";
        boolean actual = UserValidator.validatePassword(line);
        assertFalse(actual);
    }

    @Test
    public void validateNamePositiveTest() {
        String line = "Name";
        boolean actual = UserValidator.validateName(line);
        assertTrue(actual);
    }

    @Test
    public void validateNameNegativeTest() {
        String line = "Name32";
        boolean actual = UserValidator.validateName(line);
        assertFalse(actual);
    }
}