package ru.undina.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.RestaurantRepository;
import ru.undina.topjava2.repository.VoteRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.web.restaurant.RestaurantTestData.REST1_ID;
import static ru.undina.topjava2.web.restaurant.RestaurantTestData.restaurant1;
import static ru.undina.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.undina.topjava2.web.vote.VoteTestData.MATCHER;
import static ru.undina.topjava2.web.user.UserTestData.*;

public class VoteControllerTest extends AbstractControllerTest {
    static final String REST_URL = "/api/vote";
    VoteRepository voteRepository;
    RestaurantRepository restaurantRepository;

//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void createNew() throws Exception {
//        ResultActions action = perform(MockMvcRequestBuilders
//                .post(REST_URL )
//                .param("restaurantId",Integer.toString(REST1_ID))
//        .param("authUser", "user"))
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        Vote created = MATCHER.readFromJson(action);
//        int newId = created.getId();
//        Vote newVote = new Vote(newId, user, restaurant1, LocalDate.now());
//
//        MATCHER.assertMatch(created,newVote);
//        MATCHER.assertMatch(voteRepository.getById(newId),newVote);
//
//    }

}
