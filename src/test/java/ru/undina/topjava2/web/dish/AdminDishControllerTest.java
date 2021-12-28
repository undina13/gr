package ru.undina.topjava2.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.undina.topjava2.model.Dish;
import ru.undina.topjava2.repository.DishRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.util.JsonUtil.writeValue;

import static ru.undina.topjava2.web.dish.DishTestData.*;
import static ru.undina.topjava2.web.user.UserTestData.ADMIN_MAIL;

public class AdminDishControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/admin/dish/";

    @Autowired
    DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + (DISH1_ID + 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(dish2));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL  + NOT_FOUND))
//                .andDo(print())
//                .andExpect((status().isUnprocessableEntity()));
        perform(MockMvcRequestBuilders.get(REST_URL  + NOT_FOUND))
                .andDo(print())
                .andExpect(MATCHER.contentJson(dish99));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + (DISH1_ID + 6) ))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DISH1_ID + 6 ).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional
    void createNew() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders
                .post(REST_URL  )
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = MATCHER.readFromJson(action);
        int newId = created.getId();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void update() throws Exception {
        Dish updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL  + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MATCHER.assertMatch(dishRepository.findById(DISH1_ID).orElseThrow(), getUpdated());
    }

    }
