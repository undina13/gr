package ru.undina.graduation.web.vote;

import ru.undina.graduation.model.Vote;
import ru.undina.graduation.web.MatcherFactory;

import java.time.LocalDate;

import static ru.undina.graduation.web.restaurant.RestaurantTestData.restaurant1;
import static ru.undina.graduation.web.restaurant.RestaurantTestData.restaurant3;
import static ru.undina.graduation.web.user.UserTestData.admin;
import static ru.undina.graduation.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final int VOTE1_ID = 1;
    public static final int NOT_FOUND = 99;

    public static final Vote vote1 = new Vote(VOTE1_ID, user, restaurant3, LocalDate.now().minusDays(1));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, admin, restaurant3, LocalDate.now());

    public static Vote getNew() {
        return new Vote(null, user, restaurant1, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID + 1, admin, restaurant1, LocalDate.now());
    }
}
