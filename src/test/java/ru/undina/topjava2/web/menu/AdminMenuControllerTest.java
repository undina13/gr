package ru.undina.topjava2.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.undina.topjava2.model.Dish;
import ru.undina.topjava2.model.Menu;
import ru.undina.topjava2.repository.MenuRepository;
import ru.undina.topjava2.repository.RestaurantRepository;
import ru.undina.topjava2.web.AbstractControllerTest;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.util.JsonUtil.writeValue;



import static ru.undina.topjava2.web.menu.MenuTestData.MATCHER;

import static ru.undina.topjava2.web.menu.MenuTestData.*;
import static ru.undina.topjava2.web.user.UserTestData.ADMIN_MAIL;

public class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/admin/menu/";
    @Autowired
    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void createNew() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = MATCHER.readFromJson(action);
        int newId = created.getId();
        newMenu.setId(newId);
        MATCHER.assertMatch(created, newMenu);
        MATCHER.assertMatch(menuRepository.getById(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MENU1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU1_ID ))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(menu1));
    }


}
