package ru.undina.topjava2.web.vote;

import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.web.MatcherFactory;

import java.time.LocalDate;

import static ru.undina.topjava2.web.restaurant.RestaurantTestData.restaurant3;
import static ru.undina.topjava2.web.user.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "userId");
    public static final int VOTE1_ID = 1;

   // public static final Vote vote1 = new Vote(VOTE1_ID, user, restaurant3, LocalDate.now());
    public static final Vote vote2 = new Vote(VOTE1_ID+1, admin, restaurant3, LocalDate.now());

}