package com.integu.basic.spring.validations;

public class ValidationMessages {
    // BASIC
    public static final String FIELD_CANNOT_BE_LEFT_EMPTY = "Field cannot be left empty";
    public static final String RATING_BETWEEN_1_3 = "Rating must be between 1-3.";
    public static final String ONLY_POSITIVE_NUMBERS = "It is only allowed to have positive numbers.";
    public static final String DEFAULT_SERVER_ERROR = "Server error";
    public static final String DEFAULT_SUCCESS = "Success";

    // TEMPLATES
    /** TYPE, ID **/
    public static final String ITEM_DOES_NOT_EXIT = "The %s %s does not exist";
    public static final String NOT_ENOUGH_BALANCE_ON_ACCOUNT = "The account cannot transfer the request amount (%s) due to current balance (%s).";


    // FALLBACK JSON STRING
    public static final String FALLBACK_JSON_ERROR = "{\"message\":\"" + DEFAULT_SERVER_ERROR + "\"}";
}
