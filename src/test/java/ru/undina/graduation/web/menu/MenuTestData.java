package ru.undina.graduation.web.menu;

import ru.undina.graduation.model.Menu;
import ru.undina.graduation.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.undina.graduation.web.dish.DishTestData.*;
import static ru.undina.graduation.web.restaurant.RestaurantTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class,  "restaurant");

    public static final int MENU1_ID = 1;
    public static final Menu menu1 = new Menu(MENU1_ID, restaurant1, LocalDate.now(), dishMenu1);
    public static final Menu menu2 = new Menu(MENU1_ID + 1, restaurant2, LocalDate.now(), dishMenu2);
    public static final Menu menu3 = new Menu(MENU1_ID + 2, restaurant3, LocalDate.now(), dishMenu3);
    public static final Menu menu4 = new Menu(MENU1_ID + 3, restaurant1, LocalDate.now().minusDays(1), dishMenu4);
    public static final Menu menu5 = new Menu(MENU1_ID + 4, restaurant3, LocalDate.now().minusDays(1), dishMenu5);
    public static final Menu menu6 = new Menu(MENU1_ID + 5, restaurant3, LocalDate.now().minusDays(2), dishMenu5);

    public static Menu getNew() {
        return new Menu(null, restaurant1, LocalDate.now().minusDays(2), dishMenu1);
    }
    public static final List<Menu>menuList1 = List.of(menu4, menu5);
    public static final List<Menu>menuToday= List.of(menu1, menu2, menu3);

    public static final int NOT_FOUND = 99;
}
