package ru.javaops.bootjava.web;

import ru.javaops.bootjava.model.Restaurant;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingEqualsComparator(Restaurant.class);

//    public static final int NOT_FOUND = 10;
//    public static final int MEAL1_ID = START_SEQ + 3;
//    public static final int ADMIN_MEAL_ID = START_SEQ + 10;
    public static final int REST1_ID = 1;

    public static final Restaurant rest1 = new Restaurant(1, "Three Pigs");
    public static final Restaurant rest2 = new Restaurant(2, "Mad horse");

    public static final List<Restaurant> rests = List.of(rest1, rest2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(REST1_ID, "NewRestaurant");
    }
}
