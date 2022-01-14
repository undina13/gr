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

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.graduation.util.JsonUtil.writeValue;
import static ru.undina.graduation.web.restaurant.RestaurantTestData.restaurant3;
import static ru.undina.graduation.web.user.UserTestData.ADMIN_MAIL;
import static ru.undina.graduation.web.user.UserTestData.USER_MAIL;
import static ru.undina.graduation.web.user.UserTestData.user;
import static ru.undina.graduation.web.vote.VoteTestData.MATCHER;
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
                .content(writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Vote duplicateVote = new Vote(VOTE1_ID, user, restaurant3, LocalDate.now().minusDays(1));
        duplicateVote.setId(null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicateVote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE)));
    }

//use before 11:00
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void update() throws Exception {
//        Vote updated = getUpdated();
//        updated.setId(null);
//        perform(MockMvcRequestBuilders.put(REST_URL + (VOTE1_ID+1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        MATCHER.assertMatch(voteRepository.getById(VOTE1_ID+1), getUpdated());
//    }

}
