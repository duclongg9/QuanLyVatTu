package units;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    @Test
    void testValidPhones() {
        assertTrue(units.Validator.isValidPhone("0123456789"));
        assertTrue(units.Validator.isValidPhone("0987654321"));
    }

    @Test
    void testInvalidPhones() {
        assertFalse(units.Validator.isValidPhone(null));
        assertFalse(units.Validator.isValidPhone(""));
        assertFalse(units.Validator.isValidPhone("1234567890")); // does not start with 0
        assertFalse(units.Validator.isValidPhone("01234")); // too short
        assertFalse(units.Validator.isValidPhone("01234567890")); // too long
        assertFalse(units.Validator.isValidPhone("01a3456789")); // contains letters
    }
}