package ru.undina.graduation.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.undina.graduation.repository.MenuRepository;
import ru.undina.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.graduation.web.menu.MenuTestData.MATCHER;
import static ru.undina.graduation.web.menu.MenuTestData.menuToday;
import static ru.undina.graduation.web.user.UserTestData.USER_MAIL;

public class UserMenuControllerTest extends AbstractControllerTest {
    static final String REST_URL = "/api/user/menus/";
    @Autowired
 protected  MenuRepository menuRepository;

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
