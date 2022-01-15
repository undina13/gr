package ru.undina.graduation.web.menu;

import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.undina.graduation.model.Dish;
import ru.undina.graduation.model.Menu;
import ru.undina.graduation.repository.MenuRepository;
import ru.undina.graduation.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.undina.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu";
    @Autowired
   protected MenuRepository menuRepository;
    @Autowired
    RestaurantRepository restaurantRepository;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "menu", allEntries = true)
    @Transactional
    public ResponseEntity<Menu> createNew(@Valid @RequestBody Menu menu, @PathVariable Integer restaurantId) {
        log.info("create {}", menu);
        checkNew(menu);
       menu.setRestaurant(restaurantRepository.getById(restaurantId));
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "menu", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("Menu delete {}", id);
        menuRepository.deleteExisted(id);
    }

    @GetMapping("restaurants/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Menu> findAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get Menu by ID for restaurant {}", restaurantId);
        return menuRepository.findAllByRestaurant(restaurantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> get(@PathVariable Integer id,  @PathVariable Integer restaurantId) {
        log.info("get menu by id = {} ", id);
       Menu menu = menuRepository.getById(id);
        assureIdConsistent(menu.getRestaurant(), restaurantId);
        return ResponseEntity.of(menuRepository.findById(id));
    }

//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @CacheEvict(value = "menu", allEntries = true)
//    @Transactional
//    public void update(@PathVariable int id, @Valid @RequestBody Menu menu,  @PathVariable Integer restaurantId) {
//        log.info("update {} with id={}", menu, id);
//        assureIdConsistent(menu, id);
//        Menu updated = menuRepository.getById(id);
//        assureIdConsistent(updated.getRestaurant(), restaurantId);
//        menuRepository.save(menu);
//    }

    @GetMapping("/getdate")
    public List<Menu> findAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,  @PathVariable Integer restaurantId) {
        log.info("get menu by {} ", date);

        return menuRepository.findAllByDateAndRestaurantId(date, restaurantId);
    }
}
