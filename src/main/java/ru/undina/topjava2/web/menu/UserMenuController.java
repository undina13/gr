package ru.undina.topjava2.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.undina.topjava2.model.Menu;
import ru.undina.topjava2.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserMenuController {

     static final String REST_URL = "/api/user/menu/";

    MenuRepository menuRepository;

    @GetMapping("/today")
    public List<Menu> findAllByToday() {
        log.info("get menu by today ");
        LocalDate date = LocalDate.now();
        return menuRepository.findAllByDate(date);
    }
}
