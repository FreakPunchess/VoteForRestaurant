package ru.javaops.bootjava.web.restaurant;

import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int NOT_FOUND = 10;
    public static final int REST1_ID = 1;

    public static final Restaurant rest1 = new Restaurant(1, "Three Pigs");
    public static final Restaurant rest2 = new Restaurant(2, "Mad horse");

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(REST1_ID, "NewRestaurant");
    }
}
