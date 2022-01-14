package ru.undina.graduation.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.undina.graduation.model.Menu;
import ru.undina.graduation.repository.MenuRepository;
import ru.undina.graduation.web.AbstractControllerTest;
import ru.undina.graduation.web.GlobalExceptionHandler;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.graduation.util.JsonUtil.writeValue;
import static ru.undina.graduation.web.dish.DishTestData.dishMenu2;
import static ru.undina.graduation.web.menu.MenuTestData.*;
import static ru.undina.graduation.web.restaurant.RestaurantTestData.restaurant2;
import static ru.undina.graduation.web.user.UserTestData.ADMIN_MAIL;

public class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/admin/menu/";
    @Autowired
    MenuRepository menuRepository;

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
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Menu duplicateMenu = new Menu(MENU1_ID + 1, restaurant2, LocalDate.now(), dishMenu2);
        duplicateMenu.setId(null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(duplicateMenu)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_MENU)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + (MENU1_ID + 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(menu2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void findAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "getdate")
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(menuList1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + (MENU1_ID + 5)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MENU1_ID + 5).isPresent());
    }
}
