package ru.undina.graduation.web.dish;

import ru.undina.graduation.model.Dish;
import ru.undina.graduation.web.MatcherFactory;

import java.util.List;

import static ru.undina.graduation.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final int DISH1_ID = 1;
    public static final MatcherFactory.Matcher<Dish> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class,  "restaurant");
    public static final Dish dish1 = new Dish(DISH1_ID, "Сельдь под шубой", 200, restaurant1);
    public static final Dish dish2 = new Dish(DISH1_ID+1, "Заливная рыба", 300, restaurant1);
    public static final Dish dish3 = new Dish(DISH1_ID+2, "Оливье", 150, restaurant2);
    public static final Dish dish4 = new Dish(DISH1_ID+3, "Шампанское", 100, restaurant2);
    public static final Dish dish5 = new Dish(DISH1_ID+4, "Мандарины", 100, restaurant3);
    public static final Dish dish6 = new Dish(DISH1_ID+5, "Бутерброды с икрой", 500, restaurant3);
    public static final Dish dish7 = new Dish(DISH1_ID+6, "Перепел в сливочном соусе", 1500, restaurant3);
    public static final Dish dish99 = null;

    public static Dish getNew() {
        return new Dish(null, "Что-то новенькое", 1000, restaurant3);
    }

    public static Dish getUpdated(){return new Dish(DISH1_ID, "Updated Сельдь под шубой", 700, restaurant1);}

    public static final List<Dish> dishMenu1 = List.of(dish1, dish2);
    public static final List<Dish> dishMenu2 = List.of(dish3, dish4);
    public static final List<Dish> dishMenu3 = List.of(dish5, dish6);


    public static final int NOT_FOUND = 99;
}
