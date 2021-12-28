package ru.undina.topjava2.web.restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.undina.topjava2.repository.RestaurantRepository;
import ru.undina.topjava2.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.undina.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.undina.topjava2.web.user.UserTestData.ADMIN_MAIL;


public class AdminRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/admin/restaurant/";
    @Autowired
    private RestaurantRepository restaurantRepository;


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(restaurant1));
    }


}
