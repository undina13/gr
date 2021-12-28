package ru.undina.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.undina.topjava2.repository.MenuRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import static ru.undina.topjava2.web.user.UserTestData.ADMIN_MAIL;

public class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/admin/menu";
    @Autowired
    MenuRepository menuRepository;

    @Test


    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID + "/dishes/" + DISH1_ID))
//                .andExpect(status().isOk())
//                .andDo(print())
//                // https://jira.spring.io/browse/SPR-14472
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MATCHER.contentJson(dish1));
    }


}
