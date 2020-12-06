package finalproject.validator;

import com.verbovskiy.server.validator.CarValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CarValidatorTest {
    @Test
    public void validatePricePositiveTest() {
        String price = "243";
        boolean actual = CarValidator.validatePrice(price);
        assertTrue(actual);
    }

    @Test
    public void validatePriceNegativeTest() {
        String price = "243s";
        boolean actual = CarValidator.validatePrice(price);
        assertFalse(actual);
    }

    @Test
    public void validateManufactureYearPositiveTest() {
        String year = "2010";
        boolean actual = CarValidator.validateManufactureYear(year);
        assertTrue(actual);
    }

    @Test
    public void validateManufactureYearNegativeTest() {
        String year = "2021";
        boolean actual = CarValidator.validateManufactureYear(year);
        assertFalse(actual);
    }

    @Test
    public void validateDescriptionPositiveTest() {
        String description = "уркер";
        boolean actual = CarValidator.validateDescription(description);
        assertTrue(actual);
    }

    @Test
    public void validateDescriptionNegativeTest() {
        String description = "qwergdfn<>";
        boolean actual = CarValidator.validateDescription(description);
        assertFalse(actual);
    }

    @Test
    public void validateModelPositiveTest() {
        String model = "qwergdfn-_";
        boolean actual = CarValidator.validateModel(model);
        assertTrue(actual);
    }

    @Test
    public void validateModelNegativeTest() {
        String model = "qwergdfn-_<>";
        boolean actual = CarValidator.validateModel(model);
        assertFalse(actual);
    }
}