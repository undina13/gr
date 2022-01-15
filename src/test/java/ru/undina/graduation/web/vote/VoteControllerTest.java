package ru.undina.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.undina.graduation.model.Vote;
import ru.undina.graduation.repository.VoteRepository;
import ru.undina.graduation.web.AbstractControllerTest;
import ru.undina.graduation.web.GlobalExceptionHandler;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.graduation.web.restaurant.RestaurantTestData.REST1_ID;
import static ru.undina.graduation.web.user.UserTestData.ADMIN_MAIL;
import static ru.undina.graduation.web.user.UserTestData.USER_MAIL;
import static ru.undina.graduation.web.vote.VoteTestData.*;

public class VoteControllerTest extends AbstractControllerTest {
    static final String REST_URL = "/api/votes/";
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        Vote newVote = VoteTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", Integer.toString(REST1_ID)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = "user4@yandex.ru")
    void getTodayVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.MATCHER.contentJson(vote3));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", Integer.toString(REST1_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE)));
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
               perform(MockMvcRequestBuilders.put(REST_URL + (VOTE1_ID + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", Integer.toString(REST1_ID + 1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertMatch(voteRepository.getById(VOTE1_ID + 1), getUpdated());
    }


}
