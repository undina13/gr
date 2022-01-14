package ru.undina.graduation.web.dish;

import ru.undina.graduation.model.Dish;
import ru.undina.graduation.web.MatcherFactory;

import java.util.List;

public class DishTestData {
    public static final int DISH1_ID = 1;
    public static final MatcherFactory.Matcher<Dish> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class,  "restaurant");
    public static final Dish dish1 = new Dish(DISH1_ID, "Сельдь под шубой", 200);
    public static final Dish dish2 = new Dish(DISH1_ID+1, "Заливная рыба", 300);
    public static final Dish dish3 = new Dish(DISH1_ID+2, "Оливье", 150);
    public static final Dish dish4 = new Dish(DISH1_ID+3, "Шампанское", 100);
    public static final Dish dish5 = new Dish(DISH1_ID+4, "Мандарины", 100);
    public static final Dish dish6 = new Dish(DISH1_ID+5, "Бутерброды с икрой", 500);
    public static final Dish dish7 = new Dish(DISH1_ID+6, "Перепел в сливочном соусе", 1500);
    public static final Dish dish99 = null;

    public static Dish getNew() {
        return new Dish(null, "Что-то новенькое", 1000);
    }

    public static Dish getUpdated(){return new Dish(DISH1_ID, "Updated Сельдь под шубой", 700);}

    public static final List<Dish> dishMenu1 = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishMenu2 = List.of(dish3, dish4, dish5);
    public static final List<Dish> dishMenu3 = List.of(dish1, dish4, dish6);
    public static final List<Dish> dishMenu4 = List.of(dish1, dish6, dish3);
    public static final List<Dish> dishMenu5 = List.of(dish6, dish2, dish1);

    public static final int NOT_FOUND = 99;
}
