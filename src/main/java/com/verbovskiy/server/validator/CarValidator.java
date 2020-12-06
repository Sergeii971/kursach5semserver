package com.verbovskiy.server.validator;

import com.verbovskiy.server.controller.command.RequestParameter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Car validator.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class CarValidator {
    private static final String MODEL_PATTERN = "^[0-9a-zA-Z-_]{1,50}$";
    private static final String PRICE_PATTERN = "^[0-9]{1,15}$";
    private static final String DESCRIPTION_PATTERN = "[^*<>\\\\/{|}]+ {0,500}";
    private static final String YEAR_PATTERN = "[0-9]{4}";
    private static final int MIN_CAR_MANUFACTURE_YEAR = 2010;

    private CarValidator() {
    }

    /**
     * Validate car data.
     *
     * @param price the price
     * @param description the description
     * @param model the model
     * @param manufactureYear the manufacture year
     * @return incorrect parameters
     */
    public static Map<String, Boolean> validateCarData(String price, String description,
                                                       String model, String manufactureYear) {
        Map<String, Boolean> incorrectParameters = new HashMap<>();
        if (!validatePrice(price)) {
            incorrectParameters.put(RequestParameter.PRICE, true);
        }
        if (!validateDescription(description)) {
            incorrectParameters.put(RequestParameter.DESCRIPTION, true);
        }
        if (!validateModel(model)) {
            incorrectParameters.put(RequestParameter.MODEL, true);
        }
        if (!validateManufactureYear(manufactureYear)) {
            incorrectParameters.put(RequestParameter.MANUFACTURE_YEAR, true);
        }
        return incorrectParameters;
    }

    /**
     * Validate price.
     *
     * @param price the price
     * @return the boolean
     */
    public static boolean validatePrice(String price) {
        boolean isPriceCorrect = false;

        if (price != null && !price.isEmpty()) {
            Pattern pattern = Pattern.compile(PRICE_PATTERN);
            Matcher matcher = pattern.matcher(price);
            isPriceCorrect = matcher.matches();
        }
        return isPriceCorrect;
    }

    /**
     * Validate year.
     *
     * @param year the year
     * @return the boolean
     */
    public static boolean validateManufactureYear(String year) {
        boolean isYearCorrect = false;

        if (year != null && !year.isEmpty()) {
            Pattern pattern = Pattern.compile(YEAR_PATTERN);
            Matcher matcher = pattern.matcher(year);
            if (matcher.matches()) {
                int yearIntFormat = Integer.parseInt(year);
                if ((yearIntFormat >= MIN_CAR_MANUFACTURE_YEAR) && (yearIntFormat <= LocalDate.now().getYear())) {
                    isYearCorrect = true;
                }
            }
        }
        return isYearCorrect;
    }

    /**
     * Validate description.
     *
     * @param description the description
     * @return the boolean
     */
    public static boolean validateDescription(String description) {
        boolean isDescriptionCorrect = true;

        if (description != null && !description.isEmpty()) {
            Pattern pattern = Pattern.compile(DESCRIPTION_PATTERN);
            Matcher matcher = pattern.matcher(description);
            isDescriptionCorrect = matcher.matches();
        }
        return isDescriptionCorrect;
    }

    /**
     * Validate model.
     *
     * @param model the model
     * @return the boolean
     */
    public static boolean validateModel(String model) {
        boolean isModelCorrect = false;

        if (model != null && !model.isEmpty()) {
            Pattern pattern = Pattern.compile(MODEL_PATTERN);
            Matcher matcher = pattern.matcher(model);
            isModelCorrect = matcher.matches();
        }
        return isModelCorrect;
    }
}
