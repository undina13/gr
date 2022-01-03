package ru.undina.topjava2.web.menu;

import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.webjars.NotFoundException;
import ru.undina.topjava2.model.Dish;
import ru.undina.topjava2.model.Menu;
import ru.undina.topjava2.model.Restaurant;
import ru.undina.topjava2.repository.DishRepository;
import ru.undina.topjava2.repository.MenuRepository;
import ru.undina.topjava2.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.undina.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.undina.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/menu";


    MenuRepository menuRepository;



@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public ResponseEntity<Menu> createNew(@Valid @RequestBody Menu menu) {
    log.info("create {}", menu);
    checkNew(menu);
    Menu created = menuRepository.save(menu);
    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(REST_URL + "/{id}")
            .buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uriOfNewResource).body(created);
}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void delete( @PathVariable Integer id) {

        log.info("Menu delete {}", id);

        menuRepository.delete(id);
    }

    @GetMapping("restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
   public List<Menu>findAllByRestaurant(@PathVariable int restaurantId) {
        log.info("get Menu by ID for restaurant {}", restaurantId);
       return menuRepository.findAllByRestaurant(restaurantId);
    }

    @GetMapping()
    public List<Menu> getAll() {
        log.info("get all dishes ");
        return menuRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Menu>> get(@PathVariable Integer id) {
        log.info("get menu by id = {} ", id);
        Optional<Menu> menu = menuRepository.findById(id);
        return ResponseEntity.ok(menu);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Menu menu) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        menuRepository.save(menu);
    }

    @GetMapping("/getdate")
    public List<Menu> findAllByDate( @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu by {} ", date);

        return menuRepository.findAllByDate(date);
    }



}
