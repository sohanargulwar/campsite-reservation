package com.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final Integer RESERVATION_MAX_DAYS = 2;

  public static final Integer RESERVATION_MIN_DAYS = 0;

  public static final Integer RESERVATION_OPEN_FOR_NEXT_DAYS = 30;

  public static final String API_ENDPOINT = "/camping-reservation";

  public static final String GET_RESERVATION_ENDPOINT = "/reservation/{reservationUUID}";

  public static final String GET_ALL_RESERVATION_ENDPOINT = "/campsite/{campsiteID}";

  public static final String MAKE_RESERVATION_ENDPOINT = "/make-reservation";

  public static final String DELETE_RESERVATION_ENDPOINT = "/delete-reservation/{reservationUUID}";

  public static final String UPDATE_RESERVATION_ENDPOINT = "/update-reservation/{reservationUUID}";

  public static final String CREATE_CAMPSITE_ENDPOINT = "/create-campsite";

  public static final String GET_ALL_CAMPSITES = "/get-all-campsites";

  public static final String REGEX_FOR_STRING_VALIDATION = "/^[A-Za-z]+$/";
}
