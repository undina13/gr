package ru.undina.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.undina.topjava2.model.Vote;
import ru.undina.topjava2.repository.VoteRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.util.JsonUtil.writeValue;
import static ru.undina.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.undina.topjava2.web.vote.VoteTestData.MATCHER;

public class VoteControllerTest extends AbstractControllerTest {
    static final String REST_URL = "/api/vote/";
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
