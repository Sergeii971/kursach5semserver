package finalproject.util.date_converter;

import com.verbovskiy.server.util.date_converter.DateConverter;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class DateConverterTest {

    @Test
    public void convertToLongPositiveTest() {
        LocalDate date = LocalDate.of(2020, 11, 4);
        long actual = DateConverter.convertToLong(date);
        double expected = 1604437200000.;
        assertEquals(actual, expected);
    }

    @Test
    public void convertToLongNegativeTest() {
        LocalDate date = LocalDate.of(2020, 11, 4);
        long actual = DateConverter.convertToLong(date);
        double expected = 1604437;
        assertNotEquals(actual, expected);
    }

    @Test
    public void convertToDatePositiveTest() {
        long date = 1604437200;
        LocalDate actual = DateConverter.convertToDate(date);
        LocalDate expected = LocalDate.of(1970, 1, 19);
        assertEquals(actual, expected);
    }

    @Test
    public void convertToDateNegativeTest() {
        long date = 1604437200;
        LocalDate actual = DateConverter.convertToDate(date);
        LocalDate expected = LocalDate.of(1976, 1, 19);
        assertNotEquals(actual, expected);
    }
}