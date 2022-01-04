package ru.undina.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.undina.topjava2.repository.MenuRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.web.menu.MenuTestData.MATCHER;
import static ru.undina.topjava2.web.menu.MenuTestData.menuToday;
import static ru.undina.topjava2.web.user.UserTestData.USER_MAIL;

public class UserMenuControllerTest extends AbstractControllerTest {
    static final String REST_URL = "/api/user/menu/";
    @Autowired
    MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void findAllByToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(menuToday));
    }
}
