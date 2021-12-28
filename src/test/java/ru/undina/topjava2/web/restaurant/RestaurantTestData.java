package ru.undina.topjava2.web.restaurant;

import ru.undina.topjava2.model.Restaurant;
import ru.undina.topjava2.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class,  "restaurant");

    public static final int REST1_ID = 1;
    public static final Restaurant restaurant1 = new Restaurant(REST1_ID, "Снежинка");
    public static final Restaurant restaurant2 = new Restaurant(REST1_ID + 1, "Новогодний");
    public static final Restaurant restaurant3 = new Restaurant(REST1_ID + 2, "Дед Мороз");
    public static final Restaurant restaurant4 = new Restaurant(REST1_ID + 3, "Прошлогодний снег");

    public static final int NOT_FOUND = 99;
    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }
    public static Restaurant getUpdated() {
        return new Restaurant(REST1_ID, "Updated Снежинка");
    }

    public static final List<Restaurant> restaurantList = List.of(restaurant1, restaurant2, restaurant3, restaurant4);
}
