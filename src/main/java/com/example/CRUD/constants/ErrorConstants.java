package com.example.CRUD.constants;

public class ErrorConstants {
    public static final String USER_NOT_FOUND = "User with ID %d not found";
    public static final String USER_INACTIVE_OR_NOT_FOUND = "User with ID %d not found or inactive";
    public static final String INVALID_FILTER_OPTION = "Invalid filter option: %s. Allowed values: ACTIVE, INACTIVE, BOTH.";
    public static final String USER_CREATION_ERROR = "An unexpected error occurred while creating the user.";


    public static final String ERROR_NAME_GENERIC = "Name cannot be too generic.";
    public static final String ERROR_USER_NOT_FOUND = "User not found.";

    public static final String ERROR_DUPLICATE_PHONE = "User with given Phone Number already exists.";
    public static final String ERROR_DUPLICATE_EMAIL = "User with given Email ID already exists.";

    public static final String ERROR_NAME_EMPTY = "Name cannot be null or empty.";
    public static final String ERROR_NAME_PATTERN = "Name must contain only letters and spaces (3-50 characters).";
    public static final String ERROR_PHONE_EMPTY = "Phone Number cannot be null or empty.";
    public static final String ERROR_PHONE_PATTERN = "Invalid phone number format. It must be a 10-digit number starting with 1-9.";
    public static final String ERROR_EMAIL_EMPTY = "Email ID cannot be null or empty.";
    public static final String ERROR_EMAIL_PATTERN = "Invalid email format.";



}
